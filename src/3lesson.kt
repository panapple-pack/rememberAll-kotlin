import kotlin.random.Random
import kotlin.random.nextInt

fun calculateDamage(baseAttack: Int): Int {
    val variation = Random.nextInt(80, 131)
    return (baseAttack * variation) / 100
}

// функция для определения атаки одного существа другим
fun performAttack(attackerName: String, attackerAttack: Int, defenderName: String, defenderHealth: Int): Int {
    println("Ход существа $attackerName")
    val damage = calculateDamage(attackerAttack) // Вызов функции, считающий урон от атаки
    var newHealth = defenderHealth - damage

    println("$attackerName наносит $defenderName $damage урона")
    println("у $defenderName осталось $newHealth HP")
    return newHealth
}

fun startBattle() {
    val playerName = "Рутаритилиус"
    var playerHealth = 100
    var playerAttack = 10

    val monsterName = "EminemZombie"
    var monsterHealth = 60
    var monsterAttack = 12

    var round = 1
    println(">>> Бой между $playerName и $monsterName начинается <<<")

    while (playerHealth > 0 && monsterHealth > 0) {
        println("--- Раунд: $round ---")
        // Ход игрока
        monsterHealth = performAttack(playerName, playerAttack, monsterName, monsterHealth)
        if (monsterHealth <= 0) {
            monsterHealth = 0
            break
        }

        // Ход монстра
        playerHealth = performAttack(monsterName, monsterAttack, playerName, playerHealth)

        if (playerHealth <= 0) {
            playerHealth = 0
            break
        }

        round++
    }

    if (playerHealth > 0) {
        println("\n Победа! $playerName одолел $monsterName!")
    } else {
        println("\n Поражение! $monsterName одолел $playerName!")
    }
}

fun main() {
    println("==== ЗАПУСК ДВИЖКА ИГРЫ ====")
    startBattle()
    println("Игра завершена")
}