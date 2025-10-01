//import kotlin.random.Random
//
//// Улучшенный класс character - с конструктором
//// (...) Параметры в скобках - параметры главного конструктора
//
//class Character(
//    // val - параметр автоматически становится свойствами класса
//    val name: String,
//    // Без val - это просто параметр конструктора (это не свойство класса)
//    maxHealth: Int,
//    baseAttack: Int
//) {
//    // ИНКАПСУЛЯЦИЯ - делает внутренние свойста приватными (private)
//    // private - модификатор доступа. Означает, что свойство доступно для использования только внутри класса
//    private var _health = maxHealth.coerceAtLeast(1)  // coerceAtLeast гарантирует гарантированное минимальное здоровье = 1
//
//    // публичное свойство health только для чтения (val)
//    // другие классы могут узнать количество здоровья, но не могут изменить его
//    val health: Int
//        get() = _health  // get() - геттер возвращает только значение приватного _health
//
//    private val _maxHealth = maxHealth.coerceAtLeast(1)
//    val maxHealth: Int
//        get() = _maxHealth
//
//    private val _attack = baseAttack.coerceAtLeast(1)
//    val attack: Int
//        get() = _attack
//
//    // Вычисляемое свойство - свойство, которое не хранится, а вычисляется при каждом обращении
//    val isAlive: Boolean
//        get() = _health > 0
//
//    // Блок init выполняется при создании объекта (при инициализации свойств)
//    init {
//        println("Создан новый персонаж: $name (HP: $_health/$_maxHealth, АТК: $_attack)")
//    }
//
//    // Методы с проверками (инкапсуляция в деле)
//    fun takeDamage(damage: Int) {
//        if (!isAlive) {
//            println("$name уже мертв и не может получить урон")
//            return
//        }
//        val actualDamage = damage.coerceAtLeast(0)  // урон не может быть отрицательным
//        _health -= actualDamage
//        println("$name получает $actualDamage дамага! Осталось здоровья: $_health)")
//
//        if (_health <= 0) {
//            println("Наш $name пал в бою за брейнрот утопию")
//        }
//    }
//
//    fun heal(amount: Int) {
//        if (!isAlive) {
//            println("$name уже мертв и не может похилиться")
//            return
//        }
//        val healAmount = amount.coerceAtLeast(0)
//
//        _health = (_health + healAmount).coerceAtMost(_maxHealth)
//        println("$name восстанавливает себе $healAmount здоровья, теперь у него $_health/$_maxHealth HP")
//    }
//
//    fun attack(target: Character) {
//        if (!isAlive) {
//            println("$name уже мертв и не может тыщ тыщ")
//            return
//        }
//
//        if (!target.isAlive) {
//            println("$name уже мертв, хватит его бить")
//            return
//        }
//        val damage = calculateDamage(_attack)
//        println("$name атакует ${target.name}")
//        target.takeDamage(damage)
//    }
//
//    fun printStatus() {
//        val status = if (isAlive) "Жив" else "нежив"
//        println("$name: $_health/$_maxHealth HP, АТК: $_attack ($status)")
//    }
//
//}
//
//fun main() {
//    println("==== УЛУЧШЕННАЯ СИСТЕМА СОЗДАНИЯ НАШИХ ПЕРСОНАЖЕЙ ====")
//    val player = Character("Oleg", 80, 5)
//    val monster = Character("Monster", 100, 3)
//
//    player.attack(monster)
//    monster.attack(player)
//
//    player.printStatus()
//    monster.printStatus()
//
//
//    // КАК ДЕЛАТЬ НЕЛЬЗЯ
//    // player.health = 100 - Ошибка!! health - val (только для чтения)
//    // player._health = 100 - Ошибка!! _health - private
//    // ИНКАПСУЛЯЦИЯ - защищает на, чтобы не сделать что то неправильное в программе
//
//    val healer = Character("Жрец", 60, 3)
//    val warrior = Character("Щитовидный железный", 95, 8)
//
//    warrior.attack(monster)
//    healer.heal(warrior.health)
//
//}