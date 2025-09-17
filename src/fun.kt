import kotlin.random.Random

fun main() {
    val playerHealth = 100
    val playerName = "Oleg"

    println("$playerName начинает путешествие по Брейнотопии")
    println("Здоровье: $playerHealth")

    val updateHealth = attackMonster(playerHealth)
    println("После боя здоровье ваше $updateHealth")

    val healingPosion = drinkHealingPivo(updateHealth)
    println("Вы восстановили здоровье до $healingPosion")
}

fun attackMonster(health: Int): Int {
    println("Начинается битва! <<<<<<")
    // Имитация получения урона в бое с боссом
    val damage = 25
    val newHealth = health - damage

    println("Получено урона $damage")
    return newHealth; // Возвращаем обновленное здоровье
}

fun drinkHealingPivo(health: Int): Int {
    println("Вы выпили пиво <<<<<")
    val healing = Random.nextInt(20, 41)
    val newHealth = health + healing
    println("Восстановили здоровья: $healing")
    return newHealth
}