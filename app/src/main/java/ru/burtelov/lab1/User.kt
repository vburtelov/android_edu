package ru.burtelov.lab1

import androidx.annotation.VisibleForTesting
import java.math.BigInteger
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.*

class User private constructor(
    private val firstName: String,
    private val lastName: String?,
    email: String? = null,
    rawPhone: String? = null,
    meta: Map<String, Any>? = null
) {
    val userInfo: String

    private val fullName: String
        get() = listOfNotNull(firstName, lastName)
            .joinToString(" ")
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

    private val initials: String
        get() = listOfNotNull(firstName, lastName)
            .map { it.first().uppercaseChar() }
            .joinToString(" ")

    var phone: String? = null
        set(value) {
            field = value?.replace("""[^+\d]""".toRegex(), "")
        }

    private var _login: String? = null
    var login: String
        set(value) {
            _login = value.lowercase(Locale.getDefault())
        }
        get() = _login!!

    var salt: String? = null
    lateinit var passwordHash: String
    //var givenMeta: Map<String, Any>? = null

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    var accessCode: String? = null

    //for email
    constructor(
        firstName: String,
        lastName: String?,
        email: String,
        password: String
    ) : this(firstName, lastName, email = email, meta = mapOf("auth" to "password")) {
        passwordHash = encrypt(password)
    }

    // for phone
    constructor(
        firstName: String,
        lastName: String?,
        rawPhone: String
    ) : this(firstName, lastName, rawPhone = rawPhone, meta = mapOf("auth" to "sms")) {
        val code = generateAccessCode()
        passwordHash = encrypt(code)
        //println("Phone password hash is $passwordHash")
        accessCode = code
        sendAccessCodeToUser(rawPhone, code)
    }

    //for csv
    constructor(
        firstName: String,
        lastName: String?,
        email: String?,
        rawPhone: String?,
        passwordHash: String
    ) : this(firstName, lastName, email, rawPhone, meta = mapOf("src" to "csv")) {
        this.passwordHash = passwordHash
    }

    init {
        check(firstName.isNotBlank()) { "First name must not be blank" }
        check(email.isNullOrBlank() || rawPhone.isNullOrBlank()) { "Email or phone must not be blank" }

        phone = rawPhone
        login = email ?: phone!!

        userInfo = """
            firstName: $firstName
            lastName: $lastName
            login: $login
            fullName: $fullName
            initials: $initials
            email: $email
            phone: $phone
            meta: $meta
        """.trimIndent()
    }

    fun checkPassword(pass: String) = encrypt(pass) == passwordHash.also {
        println("Checking password is $passwordHash")
    }

    fun encrypt(password: String): String {
        if (salt.isNullOrEmpty()) {
            salt = ByteArray(16).also {
                SecureRandom().nextBytes(it)
            }.toString()
        }
        //println("Salt while encrypt: $salt")
        println(salt.plus(password).md5())
        return salt.plus(password).md5()
    }

    private fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(toByteArray())
        val hexString = BigInteger(1, digest).toString(16)
        return hexString.padStart(32, '0')
    }

    fun generateAccessCode(): String {
        val possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZadbcdefghijklmnopqrstuvwxyz0123456789"

        return StringBuilder().apply {
            repeat(6) {
                (possible.indices).random().also { index ->
                    append(possible[index])
                }
            }
        }.toString()
    }

    fun sendAccessCodeToUser(phone: String, code: String) {
        println(".... sending access code: $code on $phone")
    }

    companion object Factory {
        fun makeUser(
            fullName: String,
            email: String? = null,
            password: String? = null,
            phone: String? = null,
            passwordHash: String? = null
        ): User {
            val (firstName, lastName) = fullName.fullNameToPair()

            return when {
                !passwordHash.isNullOrBlank() -> User(
                    firstName, lastName, email, phone, passwordHash
                )
                !phone.isNullOrBlank() -> User(firstName, lastName, phone)
                !email.isNullOrBlank() && !password.isNullOrBlank() -> User(
                    firstName, lastName, email, password
                )

                else -> throw IllegalArgumentException("Email or phone must not be null or empty")
            }
        }
    }
}

private fun String.fullNameToPair(): Pair<String, String?> =
    this.split(" ")
        .filter { it.isNotBlank() }
        .run {
            when (size) {
                1 -> first() to null
                2 -> first() to last()
                else -> throw IllegalArgumentException(
                    "Fullname must contain only first name and last name, current split" +
                            "Fullname must contain only first name and last name, current split" +
                            "result: ${this@fullNameToPair}"
                )
            }
        }