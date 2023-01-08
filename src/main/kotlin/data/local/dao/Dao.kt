package data.local.dao

import data.local.entity.Entity
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

abstract class Dao<T>(private val tableObject: Table) {
    abstract suspend fun getAll(): List<T>
    abstract suspend fun insert(item: T)
    suspend fun insert(items: List<T>) {
        newSuspendedTransaction {
            items.forEach {
                insert(it)
            }
        }
    }
    abstract suspend fun update(item: T)
    suspend fun delete(item: T) {
        newSuspendedTransaction {
            tableObject.deleteWhere {
                (this as Entity).id eq (item as Entity).id
            }
        }
    }
    suspend fun deleteAll() {
        newSuspendedTransaction {
            tableObject.deleteAll()
        }
    }
}