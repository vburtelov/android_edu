package ru.burtelov.patterns

// Factory pattern — данный шаблон делегирует создание объектов наследникам родительского класса.

class FactoryMethod {
    interface Ball {
        fun mutate()
    }

    class Red : Ball {
        override fun mutate() {
            println("Красный мяч")
        }
    }

    class Green : Ball {
        override fun mutate() {
            println("Зеленый мяч")
        }
    }

    class Blue : Ball {
        override fun mutate() {
            println("Синий мяч")
        }
    }

    enum class BallColors {
        Red, Green, Blue
    }

    class BallFactory {
        fun showBalls(type: BallColors): Ball? {
            return when (type) {
                BallColors.Red -> Red()
                BallColors.Green -> Green()
                BallColors.Blue -> Blue()
                else -> null
            }
        }
    }
}