class Dog: Test() {

    override val name: String = "dog"
}

class Cat: Test() {

    override val name: String = "cat"
}

abstract class Test: Animal {

    override fun voice() {
        println("DOTA 2 I LOVE 4K")
    }
}

interface Animal {

    val name: String

    fun voice()
}

fun main() {
    val list = mutableListOf<Animal>()
    list.add(Dog())
    list.add(Cat())

    list.forEach {
        it.voice()
    }
}