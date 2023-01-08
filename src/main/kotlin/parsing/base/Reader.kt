package parsing.base

abstract class Reader<T, S> {

    fun read(filePath: String): List<T> {
        return getFileContent(setupSource(filePath))
    }

    abstract fun setupSource(filePath: String): S

    abstract fun getFileContent(source: S): List<T>
}