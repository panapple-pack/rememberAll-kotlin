import kotlin.random.Random

class Character {
    // Свойство класса задаются либо в скобочках, либо внутри через переменную
    var name: String = "Безымянный"
    var health: Int = 100
    val maxHealth: Int = 100
    var attack: Int = 15
    var isAlive: Boolean = true

    // Методы класса описывают поведение объекта класса
    // метод получения урона

    fun takeDamage(damage: Int) {
        health -= damage
        println("$name получает $damage урона")

        // Ссылка на текущий объект
        if (this.health <= 0) {
            isAlive = false
            println("$name пал в бою! Почестей не будет")
        }
    }

    fun heal(amount: Int) {
        if (isAlive) {
            // Убедимся, что здоровье при хиле не превысит максимум
            health = minOf(health + amount, maxHealth)
            println("$name восстанавливает $amount здоровья. Теперь здоровья $health")
        } else {
            println("$name не может быть исцелен")
        }
    }

    // Метод атаки другого персрнажа Character
    fun attack(target: Character) {
        if (!isAlive) {
            println("$name не может атаковать, так как умер")
            return // немедленный выход из метода
        }
        val damage = calculateDamage(attack)
        println("$name атакует ${target.name}")
        target.takeDamage(damage)
    }
}

fun main() {
    println("=== СОЗДАНИЕ ПЕРСОНАЖЕЙ ЧЕРЕЗ КЛАССЫ ===")

    // Создаем объекты (экземпляры класса Character)

    val player = Character()
    val monster = Character()
    player.name = "Oleg"
    player.health = 100
    player.attack = 18

    monster.name = "Nikitos-Zombi"
    monster.health = 60
    monster.attack = 22

    // Вызываем методы объектов
    println(" >>> Начало боя <<<")
    player.attack(monster)
    monster.attack(player)

    println("--- Состояние после 1 раунда ---")
    println("Игрок ${player.name} имеет ${player.health} HP, ЖИВ? ${player.isAlive}")
    println("Игрок ${monster.name} имеет ${monster.health} HP, ЖИВ? ${monster.isAlive}")

    println("--- Игрок находит зелье ---")
    player.heal(25)

}
