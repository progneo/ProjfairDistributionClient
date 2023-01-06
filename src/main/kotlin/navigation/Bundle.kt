package navigation

class Bundle {

    private val args: MutableMap<String, Any?> = mutableMapOf()

    fun put(tag: String, value: Any?) {
        args[tag] = value
    }

    fun getInt(tag: String): Int? {
        val value = args[tag]
        try {
            return value as Int?
        } catch (e: ClassCastException) {
            throw IllegalArgumentException("Type Int? was expected but ${value?.javaClass?.name} was given")
        }
    }

    fun getString(tag: String): String? {
        val value = args[tag]
        try {
            return value as String?
        } catch (e: ClassCastException) {
            throw IllegalArgumentException("Type String? was expected but ${value?.javaClass?.name} was given")
        }
    }

    fun getAny(tag: String): Any? = args[tag]
}