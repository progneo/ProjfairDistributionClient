package data.local.dao.base

import data.local.entity.base.Entity
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.BatchInsertStatement
import org.jetbrains.exposed.sql.transactions.TransactionManager
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
    //protected abstract suspend fun insertOrUpdate(it: UpdateBuilder<Number>, item: T)
}

class BatchInsertUpdateOnDuplicate(table: Table, val onDupUpdate: List<Column<*>>) :
    BatchInsertStatement(table, false) {
    override fun prepareSQL(transaction: Transaction): String {
        //println(onDupUpdate.joinToString { "${transaction.identity(it)}=${transaction.identity(it)}"})
        val onUpdateSQL = if (onDupUpdate.isNotEmpty()) {
            " ON CONFLICT(id) DO UPDATE SET " + onDupUpdate.joinToString {
                "${transaction.identity(it)}=excluded.${
                    transaction.identity(
                        it
                    )
                }"
            }
        } else ""
        return super.prepareSQL(transaction) + onUpdateSQL
    }
}

fun <T : Table, E> T.batchInsertOnDuplicateKeyUpdate(
    item: E,
    onDupUpdateColumns: List<Column<*>>,
    body: T.(BatchInsertUpdateOnDuplicate, E) -> Unit,
) {
    item?.let {
        val insert = BatchInsertUpdateOnDuplicate(this, onDupUpdateColumns)

        insert.addBatch()
        body(insert, it)

        TransactionManager.current().exec(insert)
    }
}