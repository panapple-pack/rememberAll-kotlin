import kotlin.random.Random

// Базовый класс - его важно сделать open чтобы его могли наследовать
open class Character (
    val name: String,
    maxHealth: Int,
    baseAttack: Int,
) {
    // protected - доступен для использования в классе и наследниках этого класса
    protected var _health = maxHealth.coerceAtLeast(1)
    protected var _maxHealth = maxHealth.coerceAtLeast(1)
    protected var _attack = baseAttack.coerceAtLeast(1)
    protected var _level = 1.coerceAtMost(10)
    protected var _exp = 1
    protected var _toLevelUp = 10

    // open разрешаем переопределение метода в наследниках класса
    open val health: Int
        get() = _health

    open val maxHealth: Int
        get() = _maxHealth

    open val attack: Int
        get() = _attack

    open val level: Int
        get() = _level

    open val exp: Int
        get() = _exp

    open val toLevelUp: Int
        get() = _toLevelUp

    open val isAlive: Boolean
        get() = _health > 0

    init {
        println("Создан персонаж: $name")
    }

    open fun takeDamage(damage: Int) {
        if (!isAlive) return

        val actualDamage = damage.coerceAtLeast(0)
        _health -= actualDamage
        println("$name получает $actualDamage урона! Осталось $_health HP")

        if (_health <= 0) {
            println("$name погиб")
        }
    }

    open fun heal(amount: Int) {
        if (!isAlive) return

        val healAmount = amount.coerceAtLeast(0)
        _health = (_health + healAmount).coerceAtMost(_maxHealth)
        println("$name похилился на $healAmount")
    }

    open fun attack(target: Character) {
        if (!isAlive || !target.isAlive) return

        val damage = calculateDamage(_attack)
        println("$name атакует ${target.name}!")
        target.takeDamage(damage)
        _exp += 4
    }

    open fun levelUp() {
        if (!isAlive) {
            _level = 0
            return
        }

        if (_toLevelUp >= _exp) {
            _level++
            _toLevelUp *= 2
            _attack += 1
            _maxHealth += 10
            println("$name повышен до $level уровня")
        }
    }

    open fun printStatus() {
        val status = if (isAlive) "Жив" else "нежив"
        println("$name: $_health/$_maxHealth HP, АТК: $_attack ($status)")
    }
}

// Класс наследник Warrior (дочерний класс)
// : Character(name, maxHealth, baseAttack) - наследование и вызов конструктора родителя
class Warrior(name: String, maxHealth: Int, baseAttack: Int) : Character(name, maxHealth, baseAttack) {
    // Дополнительное свойство, специфичное для класса Воина
    var armor: Int = 5

    // переопределение метода получения урона с учетом брони
    override fun takeDamage(damage: Int) {
        if (!isAlive) return

        val reducedDamage = (damage - armor).coerceAtLeast(0)
        println("Броня $name поглощает $armor урона")

        // super - вызов метода родительского класса
        super.takeDamage(reducedDamage)
    }

    // Уникальная способность воина (уникальный метод)
    fun powerfullStrike(target: Character) {
        if (!isAlive) return

        val cost = 10 // стоимость способности в очках
        if (_health > cost) {
            _health -= cost // тратим здоровье для усиленной атаки
            val damage = calculateDamage(_attack * 2)  // удвоенный урон
            println("$name использует усиленную атаку")
            target.takeDamage(damage)
        } else {
            println("У $name недостаточно HP для мощной атаки")
            attack(target)
        }
    }
}

class Mage(name: String, maxHealth: Int, baseAttack: Int) : Character(name, maxHealth, baseAttack) {
    var mana: Int = 100
    val maxMana: Int = 100

    override fun attack(target: Character) {
        if (!isAlive || !target.isAlive) return

        if (mana >= 10) {
            mana -= 10
            val damage = calculateDamage(_attack + 5) // Бонус к базовой атаке
            println("$name атакует магическим посохом и тратит 10 маны")
            target.takeDamage(damage)
            println("Осталось маны: $mana/$maxMana")
        } else {
            // обычная атака, если маны
            println("У $name недостаточно маны")
            super.attack(target)  // Вызов базовой (неизмененной) реализации атаки
        }
    }

    fun castFireball(target: Character) {
        if (!isAlive) return

        val cost = 30

        if (mana >= 30) {
            mana -= cost
            val damage = calculateDamage(_attack * 3) // тройной урон FireBall
            println("$name кастует фаербол и тратит $cost маны")
            target.takeDamage(damage)
        } else {
            attack(target)
        }
    }

    override fun printStatus() {
        val status = if (isAlive) "Живчик" else "Мертвун"
        println("$name: $_health/$_maxHealth HP, мана: $mana/$maxMana, ATK: $_attack ($status)")
    }
}

class Archer(name: String, maxHealth: Int, baseAttack: Int) : Character(name, maxHealth, baseAttack) {
    var agility = 30

    override fun attack(target: Character) {
        if (!isAlive) return

        val rand = Random.nextInt(1, 101)
        var criticalDamage: Int

        if (rand <= agility) {
            criticalDamage = calculateDamage(_attack * 2)
            println("$name наносит $criticalDamage урона")
            target.takeDamage(criticalDamage)
        } else {
            super.attack(target)
        }
    }
}










fun main() {
    println("=== БОЙ С СИСТЕМОЙ КЛАССОВ ПЕРСОНАЖЕЙ ===")

    val warrior = Warrior("Помидор", 120, 16)
    val mage = Mage("Шалун", 80, 10)
    val enemy = Warrior("Полурак", 100, 14)
    val archer = Archer("Стрелочник", 60, 9)

    println(" >>> Начало боя! <<<")
    archer.attack(enemy)
    archer.levelUp()
    archer.attack(enemy)
    archer.levelUp()
    archer.attack(enemy)
    archer.levelUp()
    archer.attack(enemy)
    archer.levelUp()
    archer.attack(enemy)
}