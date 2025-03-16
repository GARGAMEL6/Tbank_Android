open class Object(val id: Int, var available: Boolean, val title: String) {

    fun showQuickInfo() {
        println("$title доступна: ${if (available) "Да" else "Нет"}")
    }

    open fun takeHome() {
        println("Этот объект нельзя взять домой")
    }

    open fun readHere() {
        println("Этот объект нельзя взять домой")
    }

    fun returnObject() {
        if (available) {
            println("Объект уже доступен")
            return
        }

        available = true
        println("Объект $id возвращен")
    }

    open fun showFullInfo() {

    }
}

class Book(
    val idBook: Int,
    var availableBook: Boolean,
    val titleBook: String,
    val countOfLists: Int,
    val author: String
): Object(idBook, availableBook, titleBook) {

    override fun showFullInfo() {
        println("Книга: $title($countOfLists стр.) автора: $author с id $id доступна: ${if (available) "Да" else "Нет"}")
    }

    override fun takeHome() {
        if (available.not()) {
            println("Объект временно недоступен")
            return
        }

        available = false
        println("Книгу $id взяли домой")
    }

    override fun readHere() {
        if (available.not()) {
            println("Объект временно недоступен")
            return
        }

        available = false
        println("Книгу $id взяли в читальный зал")
    }

}

class Paper(
    val idPaper: Int,
    var availablePaper: Boolean,
    val titlePaper: String,
    val number: Int
): Object(idPaper, availablePaper, titlePaper) {

    override fun showFullInfo() {
        println("Выпуск №$number газеты $title с id $id доступен: ${if (available) "Да" else "Нет"}")
    }

    override fun readHere() {
        if (available.not()) {
            println("Объект временно недоступен")
            return
        }

        available = false
        println("Газету $id взяли в читальный зал")
    }
}

class Disk(
    val idDisk: Int,
    var availableDisk: Boolean,
    val titleDisk: String,
    val type: String
): Object(idDisk, availableDisk, titleDisk) {

    override fun showFullInfo() {
        println("$type $title с id $id доступен: ${if (available) "Да" else "Нет"}")
    }

    override fun takeHome() {
        if (available.not()) {
            println("Объект временно недоступен")
            return
        }

        available = false
        println("Диск $id взяли домой")
    }

}

class LibraryManager {
    val list_of_objects = mutableListOf<Object>().apply {
        add(Book(4322, true, "Война и мир", 1000, "Лев толстой"))
        add(Paper(5242, true, "Комс. правда", 321))
        add(Book(4521, false, "Недоросль", 500, "Фонвизин"))
        add(Disk(6321, false, "Cyberpunk", "CD"))
        add(Paper(5312, false, "Ералаш", 21))
        add(Disk(6153, true, "Queen", "DVD"))
    }

    fun show(type: String) {
        var second_list: List<Object> = list_of_objects
        when (type) {

            "Book" -> {
                second_list = list_of_objects.filterIsInstance<Book>()
            }
            "Paper" -> {
                second_list = list_of_objects.filterIsInstance<Paper>()
            }
            "Disk" -> {
                second_list = list_of_objects.filterIsInstance<Disk>()
            }

        }

        var count = 1
        second_list.forEach {
            print("${count++} ")
            it.showQuickInfo()
        }

        println("Выберите объект по номеру")
        val number = readlnOrNull()?.toIntOrNull() ?: 0
        if ((number in 1..<count).not()) {
            println("Несуществующий номер!")
            return
        }

        println(
            """
        1) Взять домой
        2) Читать в чит. зале
        3) Показать подробно
        4) Вернуть
        5) Выход
    """.trimIndent()
        )

        second_list[number - 1].run {
            when (readlnOrNull()?.toIntOrNull()) {
                1 -> takeHome()
                2 -> readHere()
                3 -> showFullInfo()
                4 -> returnObject()
                5 -> return
                else -> return
            }
        }



    }

}

fun main() {

    val manager = LibraryManager()

    while (true) {
        println(
            """
        1) Показать книги
        2) Показать газеты
        3) Показать диски
        4) Выход
    """.trimIndent()
        )

        manager.run {
            when(readlnOrNull()?.toIntOrNull()) {
                1 -> show("Book")
                2 -> show("Paper")
                3 -> show("Disk")
                4 -> return
                else -> println("Ошибка! Введите число от 1 до 4")
            }
        }

    }
}
