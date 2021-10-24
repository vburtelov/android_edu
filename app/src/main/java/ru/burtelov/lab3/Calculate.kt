package ru.burtelov.lab3

class Calculate {

    companion object {

        var field1: Int? = null
        var field2: Int? = null

        var operation: String = ""

        fun calculate(): Int? {
            var result: Int? = null
            if (field1 != null && field2 != null) {
                when (operation) {
                    "+" -> result = field1!! + field2!!
                    "-" -> result = field1!! - field2!!
                    "*" -> result = field1!! * field2!!
                    "/" -> result = field1!! / field2!!
                }
            }
            return result
        }

    }

}