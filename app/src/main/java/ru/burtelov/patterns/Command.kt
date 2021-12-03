package ru.burtelov.patterns

// Command Pattern - преобразует запрос на выполнение действия в отдельный объект-команду.
// Такая инкапсуляция позволяет передавать эти действия другим функциям и объектам
// в качестве параметра, приказывая им выполнить запрошенную операцию.

class CommandPattern {
    interface Command {
        fun execute()
    }
    class Receiver {
        fun turnOn() {
            println("The light is on")
        }
        fun turnOff() {
            println("The light are off")
        }
    }

    class TurnOnCommand(private val receiver: Receiver): Command {
        override fun execute() {
            receiver.turnOn()
        }
    }
    class TurnOffCommand(private val receiver: Receiver): Command {
        override fun execute() {
            receiver.turnOff()
        }
    }

    class Invoker(
        private var turnOn: TurnOnCommand,
        private var turnOff: TurnOffCommand,
    ) {
        fun turnOnLight() {
            turnOn.execute()
        }
        fun turnOffLight() {
            turnOff.execute()
        }
    }
}