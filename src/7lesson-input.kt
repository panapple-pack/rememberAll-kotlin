import kotlin.random.Random

open class Character(val name: String, var health: Int, var attack: Int) {
    val isAlive: Boolean get() = health > 0

    open fun takeDamage(damage: Int) {
        health -= damage
        println("$name получает $damage")
        if (health <= 0) println("$name пал в бою")
    }

    fun attack(target: Character) {
        if (!isAlive || !target.isAlive) return
        val damage = Random.nextInt(attack - 3, attack + 4)  // случайный урон в диапазоне
        println("$name получает ${target.name}!")
        target.takeDamage(damage)
    }
}

class Player(name: String, health: Int, attack: Int) : Character(name, health, attack) {
    var potions = 3  // количество зельев здоровья (индивидуальный атрибут (параметр) дочернего класса)
    var armor: Boolean = false

    fun usePotions() {
        if (potions > 0) {
            val healAmount = 30
            health += healAmount
            potions--
            println("$name использует зелье +$healAmount. Теперь у него $health HP")
        } else {
            println("У $name нет зельев")
        }
    }

    fun printStatus() {
        println("=== $name ===")
        println("HP: $health")
        println("ATK: $attack")
        println("Зелья: $potions")
        println("=================")
    }

    fun shield() {
        armor = true
    }

    override fun takeDamage(damage: Int) {
        if (armor) {
            health -= damage / 2
            println("$name получает $damage")
        } else {
            super.takeDamage(damage)
        }
    }
}

class GameInput {
    // Функция получения числа от пользователя
    fun getNumberInput(prompt: String, min: Int = 1, max: Int = 10): Int {
        while (true) {
            print(prompt)

            try {
                val input = readln()  // читаем ввод пользователя
                // .toInt()
                val number = input.toInt()

                // проверка, попадает ли число в допустимый диапазон min max
                if (number in min .. max) {
                    return number  // возвращает корректное число
                } else {
                    println("пж, введи норм число от $min до $max")
                }
            } catch (e: NumberFormatException) {
                println("Ошибка! Нужно ввести число!")
            }
        }  // бесконечный цикл, пока мы его не прервем
    }

    fun getYesNoInput(prompt: String): Boolean {
        while (true) {
            println("$prompt (д/н): ")
            val input = readln().lowercase()  // Приводит строку к нижнему регистру

            when(input){
                "д", "да", "y", "yes" -> return true
                "н", "нет", "n", "no" -> return false
                else -> print("Пожалуйста, введите 'да' или 'нет'")
            }
        }
    }
}

fun main() {
    println("=== Система поиска данных ===")

    val gameInput = GameInput()
    val player = Player("Игрок", 100, 15)

    println("Создайте вашего персонажа: ")
    print("Введите имя игрока: ")
    val playerName = readln()
    // Пересоздаем игрока с введенным именем
    val customPlayer = Player(if (playerName.isBlank()) "Безымянный" else playerName, 100, 15)
    val playerLevel = gameInput.getNumberInput("Введите уровень сложности (1-легко, 5-сложно): ", 1, 5)
    println("Выбран уровень сложности: ${playerLevel}, нет пути домой")

    var gameRunning = true

    while (gameRunning) {
        println("=== ГЛАВНОЕ МЕНЮ ===")
        println("1. Посмотреть статус")
        println("2. Использовать зелье")
        println("1. Выйти из игры")

        val choice = gameInput.getNumberInput("Введите действие: 1, 2, 3, 4  ")
        when(choice) {
            1 -> customPlayer.printStatus()
            2 -> customPlayer.usePotions()
            3 -> {
                gameRunning = false
                println("Выход из игры")
            }
            4 -> {
                customPlayer.shield()
            }
        }
    }
    println("Спасибо за невероятную игру, ты же вернешься?")
}