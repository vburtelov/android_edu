package ru.burtelov.lab1

import androidx.annotation.VisibleForTesting

object UserHolder {

    private val map = mutableMapOf<String, User>()
    private val phoneFormat = Regex("""^[+][\d]{11}""")

    fun registerUser(
        fullName: String,
        email: String,
        password: String
    ): User = User.makeUser(fullName, email = email, password = password)
        .also { user ->
            if (map.containsKey(user.login)) throw IllegalArgumentException("A user with this email already exists")
            else map[user.login] = user
        }

    fun loginUser(login: String, password: String): String? {
        val phoneLogin = cleanPhone(login)
        return if (phoneLogin.isNotEmpty()) {
            map[phoneLogin]
        } else {
            map[login.trim()]
        }?.let {
            if (it.checkPassword(password)) it.userInfo
            else null
        }
    }


    fun registerUserByPhone(fullName: String, rawPhone: String): User = User.makeUser(fullName = fullName, phone = rawPhone)
        .also { user ->
            if (map.containsKey(user.phone))
                throw IllegalArgumentException("Phone already used")
            if (cleanPhone(rawPhone).matches("^\\+?[0-9]{11}\$".toRegex()))
                map[user.login] = user
            else
                throw IllegalArgumentException("Phone is incorrect")
        }

    fun requestAccessCode(login: String) {
        val phone = cleanPhone(login)
        val user = map[phone];
        if (user != null) {
            val accessCode = user.generateAccessCode()
            user.accessCode = accessCode;
            user.passwordHash = user.encrypt(accessCode)
            user.sendAccessCodeToUser(phone,accessCode)
            map[phone] = user
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun clearHolder() {
        map.clear()
    }

    private fun cleanPhone(phone: String): String {
        val clnPhone = phone.replace("""[^+\d]""".toRegex(), "");
        return clnPhone
    }
}