//import kotlin.random.Random
//
//// Базовый Класс - его важно сделать open чтобы его смогли наследовать
//open class Character(
//    val name: String,
//    maxHealth: Int,
//    baseAttack: Int,
//    killPoint: Int,
//    deathPoint: Int
//){
//    // protected - доступен для использования в классе и наследниках этого класса
//    protected var _health = maxHealth.coerceAtLeast(1)
//    protected var _maxHealth = maxHealth.coerceAtLeast(1)
//    protected var _attack = baseAttack.coerceAtLeast(1)
//    protected val _killPoint = killPoint.coerceAtLeast(0)
//    protected val _deathPoint = deathPoint.coerceAtLeast(0)
//    protected var _level = 1.coerceAtMost(10)
//    protected var _xp = 0
//    protected var _xpToLevelUp = 10
//
//
//    // open - разрешаем переопределения метода в наследниках класса
//    open val health: Int
//        get() = _health.coerceAtLeast(0)
//
//    open val maxHealth: Int
//        get() = _maxHealth.coerceAtLeast(0)
//
//    open val attack: Int
//        get() = _attack
//
//    open val killPoint: Int
//        get() = _killPoint
//
//    open val deathPoint: Int
//        get() = _deathPoint
//
//    open val level: Int
//        get() = _level
//
//    open val xp: Int
//        get() = _xp
//
//    open val xpToLevelUp: Int
//        get() = _xpToLevelUp
//
//    open val isAlive: Boolean
//        get() = _health > 0
//
//    init {
//        println("Создан персонаж: $name")
//    }
//
//    open fun levelUp() {
//        if (_level == 10 || !isAlive) return
//
//        while (_xp >= _xpToLevelUp) {  // Если xp хватает, чтобы апнуть лвл на 1 и более
//            _level++
//            _xp -= _xpToLevelUp
//            _xpToLevelUp *= 2
//            val buffAttack = Random.nextInt(2,5)
//            _attack += buffAttack
//            val buffMaxHealth = Random.nextInt(6, 13)
//            _maxHealth += buffMaxHealth
//            println("$name повышен до $level уровня! Атака +$buffAttack, макс. здоровья +$buffMaxHealth")
//        }
//    }
//
//    open fun takeDamage(damage: Int){
//        if (!isAlive) return
//
//        val actualDamage = damage.coerceAtLeast(0)
//        _health -= actualDamage
//        println("$name получает $actualDamage урона! Осталось: $health")
//
//        if (_health <= 0){
//            println("$name busted")
//            _level = 1
//        }
//    }
//
//    open fun heal(amount: Int){
//        if (!isAlive) return
//
//        val healAmount = amount.coerceAtLeast(0)
//        _health = (_health + healAmount).coerceAtLeast(_maxHealth)
//        println("$name похилился на $healAmount")
//    }
//
//    open fun attack(target: Character){
//        if (!isAlive || !target.isAlive) return
//
//        val damage = calculateDamage(_attack)
//        println("$name атакует ${target.name}!")
//        target.takeDamage(damage)
//
//        if (target.health <= 0) {
//            _xp += (_killPoint + _deathPoint)
//            levelUp()
//            println("$name $level уровня получает ${_killPoint + _deathPoint} XP за убийство ${target.name}. Всего опыта: $xp/$xpToLevelUp")
//        }
//    }
//
//
//    open fun printStatus(){
//        val status = if (isAlive) "Жив" else "Нежив"
//        println("$name $level: $health/$maxHealth HP, ATK: $attack ($status)")
//    }
//
//}
//
//// класс наследник Warrior (Дочерний класс)
//// : Character(name, maxHealth, baseAttack) - наследование и вызо конструктора родителя
//class Warrior(name: String, maxHealth: Int, baseAttack: Int, killPoint: Int, deathPoint: Int) : Character(name, maxHealth, baseAttack, killPoint, deathPoint){
//
//    // Дополнительное свойство, спецефичное для Класса Воина
//    var armor: Int = 5
//
//    // Переопределение метода получения урона с учетом брони
//    override fun takeDamage(damage: Int) {
//        if (!isAlive) return
//
//        val reducedDamage = (damage - armor).coerceAtLeast(0)
//        println("Броня $name поглощает $armor урона")
//        // super - вызов метода родительского класса
//        super.takeDamage(reducedDamage)
//    }
//
//    // Уникальная способность воина (Уникальный метод)
//    fun powerfulStrike(target: Character){
//        if (!isAlive) return
//
//        val cost = 10
//        if (_health > cost){
//            _health -= cost // Тратим здоровье для усиленой атаки
//            val damage = calculateDamage(_attack * 2) // Удвоенный урон
//            println("$name использует ульту")
//            target.takeDamage(damage)
//            if (target.health <= 0) {
//                _xp += (_killPoint + _deathPoint)
//                levelUp()
//                println("$name $level уровня получает ${_killPoint + _deathPoint} XP за убийство ${target.name}. Всего опыта: $xp/$xpToLevelUp")
//            }
//            levelUp()
//        }else{
//            println("у $name недостаточно НР для мощной атаки")
//            attack(target)
//        }
//    }
//}
//
//class Mage(name: String, maxHealth: Int, baseAttack: Int, killPoint: Int, deathPoint: Int) : Character(name, maxHealth, baseAttack, killPoint, deathPoint){
//
//    var mana: Int = 100
//    val maxMana: Int = 100
//
//    override fun attack(target: Character) {
//        if (!isAlive || !target.isAlive) return
//
//        if(mana >= 10){
//            mana -= 10
//            val damage = calculateDamage(_attack + 5) // Бонус к базовой
//            println("$name атакует магическим посохом и тратит 10 маны")
//            target.takeDamage(damage)
//            println("Осталось маны: $mana/$maxMana")
//            if (target.health <= 0) {
//                _xp += (_killPoint + _deathPoint)
//                levelUp()
//                println("$name $level уровня получает ${_killPoint + _deathPoint} XP за убийство ${target.name}. Всего опыта: $xp/$xpToLevelUp")
//            }
//        }else{
//            // Обычная атака, если маны
//            println("У $name недостаточно маны")
//            super.attack(target) // Вызов базовой (не измененной) реализации в атаки
//        }
//
//    }
//
//    fun castFireball(target: Character){
//        if (!isAlive) return
//
//        val cost = 30
//        if (mana >= 30){
//            mana -= cost
//            val damage = calculateDamage(_attack * 3) // Тройной урона Fireballs
//            println("$name кастует fireball и тратит $cost маны")
//            target.takeDamage(damage)
//            if (target.health <= 0) {
//                _xp += (_killPoint + _deathPoint)
//                levelUp()
//                println("$name $level уровня получает ${_killPoint + _deathPoint} XP за убийство ${target.name}. Всего опыта: $xp/$xpToLevelUp")
//            }
//        }else{
//            attack(target)
//        }
//    }
//
//    override fun printStatus() {
//        val status = if (isAlive) "Живчик" else "Нежить"
//        println("$name $level: $health/$maxHealth HP, Mana: $mana/$maxMana, ATK: $attack ($status)")
//    }
//}
//
//class Archer(name: String, maxHealth: Int, baseAttack: Int, killPoint: Int, deathPoint: Int) : Character(name, maxHealth, baseAttack, killPoint, deathPoint) {
//    val agility = 30
//
//    override fun attack(target: Character) {
//        if (!isAlive || !target.isAlive) return
//
//        val rand = Random.nextInt(1, 101)
//        if (rand <= agility) {
//            val damage = calculateDamage(_attack * 2)
//            println("$name наносит двойной урон благодаря ловкости")
//            target.takeDamage(damage)
//            if (target.health <= 0) {
//                _xp += (_killPoint + _deathPoint)
//                levelUp()
//                println("$name $level уровня получает ${_killPoint + _deathPoint} XP за убийство ${target.name}. Всего опыта: $xp/$xpToLevelUp")
//            }
//        } else {
//            super.attack(target)
//        }
//    }
//}
//
//
//fun main(){
//    println("=== Бой с системой классов персонажей ===")
//
//    val warrior = Warrior("Воин", 120, 16, 2, 4)
//    val mage = Mage("Маг", 80, 10, 4, 4)
//    val enemy = Warrior("Враг", 100, 14, 3, 5)
//    val archer = Archer("Лучница", 70, 8,5, 3)
//    val enemyArcher = Archer("Лучница", 70, 8,5, 3)
//    val attackerMyAss = Mage("Маг", 80, 10, 4, 4)
//
//
//    println(">>> Начало боя <<<")
//    archer.attack(mage)
//    archer.attack(mage)
//    archer.attack(mage)
//    archer.attack(mage)
//    archer.attack(mage)
//    archer.attack(mage)
//    archer.attack(mage)
//    archer.attack(mage)
//    archer.attack(mage)
//    archer.attack(mage)
//    archer.attack(mage)
//    archer.attack(mage)
//    archer.attack(mage)
//    archer.attack(mage)
//    archer.attack(warrior)
//    archer.attack(warrior)
//    archer.attack(warrior)
//    archer.attack(warrior)
//    archer.attack(warrior)
//    archer.attack(warrior)
//    archer.attack(warrior)
//    archer.attack(warrior)
//    archer.attack(warrior)
//    archer.attack(warrior)
//    archer.attack(warrior)
//    archer.attack(warrior)
//    archer.attack(warrior)
//    archer.attack(warrior)
//    archer.attack(warrior)
//    archer.attack(warrior)
//    archer.attack(warrior)
//    archer.attack(warrior)
//    archer.attack(warrior)
//    archer.attack(warrior)
//    archer.attack(warrior)
//    archer.attack(warrior)
//    archer.attack(warrior)
//    archer.attack(warrior)
//    archer.attack(warrior)
//    archer.attack(warrior)
//    archer.attack(warrior)
//    archer.attack(warrior)
//    archer.attack(warrior)
//    archer.attack(warrior)
//    archer.attack(warrior)
//    archer.attack(warrior)
//    archer.attack(warrior)
//    archer.attack(enemy)
//    archer.attack(enemy)
//    archer.attack(enemy)
//    archer.attack(enemy)
//    archer.attack(enemy)
//    archer.attack(enemy)
//    archer.attack(enemy)
//    archer.attack(enemy)
//    archer.attack(enemy)
//    archer.attack(enemy)
//    archer.attack(enemy)
//    archer.attack(enemy)
//    archer.attack(enemy)
//    archer.attack(enemy)
//    archer.attack(enemy)
//    archer.attack(enemy)
//    archer.attack(enemy)
//    archer.attack(enemy)
//    archer.attack(enemy)
//    archer.attack(enemy)
//    archer.attack(enemy)
//    archer.attack(enemy)
//    archer.attack(enemy)
//    archer.attack(enemy)
//    archer.attack(enemy)
//    archer.attack(enemy)
//    archer.attack(enemy)
//    archer.attack(enemy)
//    archer.attack(enemy)
//    archer.attack(enemy)
//    archer.attack(enemy)
//    archer.attack(enemy)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    archer.attack(enemyArcher)
//    attackerMyAss.attack(archer)
//    attackerMyAss.attack(archer)
//    attackerMyAss.attack(archer)
//    attackerMyAss.attack(archer)
//    attackerMyAss.attack(archer)
//    attackerMyAss.attack(archer)
//    attackerMyAss.attack(archer)
//    attackerMyAss.attack(archer)
//    attackerMyAss.attack(archer)
//    attackerMyAss.attack(archer)
//    attackerMyAss.attack(archer)
//    attackerMyAss.attack(archer)
//    attackerMyAss.attack(archer)
//    attackerMyAss.attack(archer)
//    attackerMyAss.attack(archer)
//    attackerMyAss.attack(archer)
//    attackerMyAss.attack(archer)
//    attackerMyAss.attack(archer)
//    attackerMyAss.attack(archer)
//    attackerMyAss.attack(archer)
//    attackerMyAss.attack(archer)
//    attackerMyAss.attack(archer)
//    attackerMyAss.attack(archer)
//    attackerMyAss.attack(archer)
//    attackerMyAss.attack(archer)
//    attackerMyAss.attack(archer)
//    attackerMyAss.attack(archer)
//    attackerMyAss.attack(archer)
//    attackerMyAss.attack(archer)
//    attackerMyAss.attack(archer)
//    attackerMyAss.attack(archer)
//    attackerMyAss.attack(archer)
//    attackerMyAss.attack(archer)
//    attackerMyAss.attack(archer)
//    attackerMyAss.attack(archer)
//    attackerMyAss.attack(archer)
//
//
//    println(" --- Статусы персонажей  ---")
//    mage.printStatus()
//    warrior.printStatus()
//    archer.printStatus()
//
//}