import kotlin.random.Random

class Item(val name: String, val description: String, val useEffect: (Player) -> Unit) {
    fun use(player: Player) {
        println("Используется $name")
        useEffect(player)
    }
}

class Location(val name: String, val description: String) {
    val enemies = mutableListOf<Character>()
    val items = mutableListOf<Item>()

    fun addEnemy(enemy: Character) {
        enemies.add(enemy)
    }


    fun addItems(item: Item) {
        items.add(item)
    }


    fun describe() {
        println("=== $name ===")
        println(description)

        if (enemies.isNotEmpty()) {
            println("Враги в локации: ")
            //  forEachIndexed - перебирает список (массив), предоставляя индексы и элементы под этими индексами
            enemies.forEachIndexed { index, enemy ->
                println("${index + 1}. ${enemy.name} (HP: ${enemy.health})")
            }
        }

        if (items.isNotEmpty()) {
            println("Предметы в локации: ")
            //  forEachIndexed - перебирает список (массив), предоставляя индексы и элементы под этими индексами
            items.forEachIndexed { index, item ->
                println("${index + 1}. ${item.name} - ${item.description}")
            }
        }
    }
}

class GameWorld {
    private val locations = mutableListOf<Location>()
    private var currentLocationIndex = 0
    val gameInput = GameInput()

    // Вычисляемое свойство для текущей локации
    val currentLocation: Location
        get() = locations[currentLocationIndex]


    fun createWorld() {
        val forest = Location("Темный лес", "Густой хвойный лес")
        val cave = Location("Пещера темная", "Темная пещера не такая страшная, как лес, но страшная")
        val village = Location("Деревья", "Мирная зона, здесь кайфово, пока не придет голем")

        // Создаем врагов
        val oleg = Character("Олег", 40, 8)
        val wolf = Character("volk", 25, 10)
        val wildGolem = Character("Дикий кукуруз", 200, 15)

        // Создаем предметы локации
        val healPotions = Item("Зелье здоровья", "Восстанавливает от -1 до 1 хп", { player ->
            println("${player.name} восстанавливает ... HP")
        })
        val attackPotion = Item("Зелье силы", "Увеличивает атаку на 5", { player ->
            println("${player.name} усиливает себя на 5")
        })

        // РАСПРЕДЕЛЕНИЕ ВРАГОВ И ПРЕДМЕТОВ ПО ЛОКАЦИЯМ

        forest.addEnemy(wolf)
        forest.addEnemy(oleg)
        forest.addItems(healPotions)

        cave.addEnemy(wildGolem)
        cave.addEnemy(oleg)
        cave.addItems(attackPotion)

        village.addItems(healPotions)

        // добавим локации в наш мир
        locations.add(forest)
        locations.add(cave)
        locations.add(village)
    }


    private fun combatMenu(player: Player) {
        if (currentLocation.enemies.isEmpty()) {
            println("Здесь нет врагов, чтобы с ними файтиться")
            return
        }

        println("Выберите цель для атаки: ")
        currentLocation.enemies.forEachIndexed { index, enemy ->
            println("${index + 1}. ${enemy.name} (HP: ${enemy.health})")
        }
        println("${currentLocation.enemies.size + 1}. Отмена")

        val choice = gameInput.getNumberInput("Ваш выбор: ", 1, currentLocation.enemies.size + 1)

        if (choice <= currentLocation.enemies.size) {
            val target = currentLocation.enemies[choice - 1]
            player.attack(target)

            // удаление врага, если он умер
            if (!target.isAlive) {
                currentLocation.enemies.remove(target)
                println("${target.name} повержен и удален из локации!")
            }

            // атака врага в ответ (провокация)
            currentLocation.enemies.forEach { enemy ->
                if (enemy.isAlive) {
                    enemy.attack(player)
                }
            }
        }
    }

    private fun takeItemMenu(player: Player) {
        if (currentLocation.items.isEmpty()) {
            println("Здесь нет предметов для лутинга")
            return
        }

        println("\n Выберите предмет для лутинга: ")
        currentLocation.items.forEachIndexed { index, item ->
            println("${index + 1}. ${item.name}")
        }

        val choice = gameInput.getNumberInput("Ваш выбор: ", 1, currentLocation.items.size)
        val item = currentLocation.items[choice - 1]

        println("Вы взяли ${item.name}")
        // Здесь в реальной игре надо положить предмет в инвертарь игрока

//      item.use(player)
        currentLocation.items.remove(item)
    }



    private fun moveToNextLocation() {
        if (currentLocationIndex < locations.size - 1) {
            currentLocationIndex++
            println("Вы переместились на локацию: ${currentLocation.name}")

            if (currentLocation.enemies.isNotEmpty()) {
                println("Вы слышите что-то жуткое")
            }
        } else {
            println("Это последняя локация, дальше пути нет!!!")
        }
    }




    fun startGame(player: Player) {
        println("Добро пожаловать в игру, ${player.name}")

        var gameRunning = true

        while (gameRunning && player.isAlive) {
            // Отображаем текущую локацию
            currentLocation.describe()
            // Показывает доступные действия игрока
            println("\n >>> ДОСТУПНЫЕ ДЕЙСТВИЯ <<<")
            println("1. Осмотреться")
            println("2. Атаковать врага")
            println("3. Взять предмет")
            println("4. Перейти на следующую локацию")
            println("5. Использовать зелье")
            println("6. Выйти из игры")

            val choice = gameInput.getNumberInput("Выберите действие: ", 1, 6)

            when(choice) {
                1 -> currentLocation.describe()
                2 -> combatMenu(player)
                3 -> takeItemMenu(player)
                4 -> moveToNextLocation()
                5 -> player.usePotions()
                6 -> gameRunning = false
            }

            if (!player.isAlive) {
                println("Игра закончена, ${player.name} пал в бою")
            }
        }
    }
}

fun main() {
    println("=== СОЗДАНИЕ ИГРОВОГО МИРА ===")

    val gameWorld = GameWorld()
    gameWorld.createWorld()

    val player = Player("Иннокентий", 100, 15)
    gameWorld.startGame(player)
}