import kotlin.random.Random

fun main() {
    val playerAttack: Int = 15
    val monsterHealth: Int = 50

    println("На вас напал брейнрот монстр")

    // Копирование переменной со здоровьем монстра для изменения
    var currentMonsterHealth = monsterHealth

    while (currentMonsterHealth > 0) {    // Random.nextInt() - функция, возвращающая Int число
        val damageVariation = Random.nextInt(80, 121) // Число, случайное от 80 до 120
        val actualDamage = (playerAttack * damageVariation) / 100  //Вычисляем фактический урон

        currentMonsterHealth -= actualDamage
        println("Вы наносите монстру $actualDamage урона")

        if (currentMonsterHealth <= 0) {
            println("Вы победили Брейнротопия не останется в долгу")
        } else {
            println("У монстра осталось $currentMonsterHealth здоровья")
        }
    }

    
}