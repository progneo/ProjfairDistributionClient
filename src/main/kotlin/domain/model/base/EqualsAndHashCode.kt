package domain.model.base

abstract class EqualsAndHashCode {
    abstract fun members(): List<Any?>

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        val otherObj = other as EqualsAndHashCode

        return members() == otherObj.members()
    }

    override fun hashCode(): Int {
        return members().hashCode()
    }
}