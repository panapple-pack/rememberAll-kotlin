class Player {
    var name: String = "Безымянный герой" // свойства, параметры, которые определяет функцию
    var health: Int = 100
    var damage: Int = 1
    var isAlive: Boolean = true

    // damage - параметр метода takeDamage
    fun takeDamage(damage: Int) {
        health -= damage
        println("$name получает $damage урона! Осталось здоровья: $health")

        if(this.health <= 0) {
            isAlive = false
            println("$name пал в битве")
        }
    }

    fun heal(amount: Int) {
        if (isAlive) {
            health += amount
            println("$name восстанавливает $amount здоровья. Теперь у него $health HP")
        } else {
            println("$name уже мертв и не может быть исцелен")
        }
    }
}

fun main() {
    // Создание объекта (экземпляр класса) Player
    val warrior = Player()
    warrior.name = "Oleg"
    println("Игрок ${warrior.name} появился в мире, здоровье: ${warrior.health}")

    // точка = это вывод метода объекта
    warrior.takeDamage(30) // герой получает 30 урона
    warrior.heal(10)
}