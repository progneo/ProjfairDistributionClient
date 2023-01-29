package data.local.dao

import data.local.dao.base.Dao
import data.local.dao.base.batchInsertOnDuplicateKeyUpdate
import data.local.entity.Supervisor
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update

object SupervisorDao: Dao<domain.model.Supervisor>(Supervisor) {

    override suspend fun getAll(): List<domain.model.Supervisor> {
        return newSuspendedTransaction {
            val supervisors = mutableListOf<domain.model.Supervisor>()
            Supervisor.selectAll().forEach {
                supervisors.add(
                    domain.model.Supervisor(
                        id = it[Supervisor.id],
                        name = it[Supervisor.name]
                    )
                )
            }
            supervisors
        }
    }

    override suspend fun insert(item: domain.model.Supervisor) {
        newSuspendedTransaction {
            Supervisor.batchInsertOnDuplicateKeyUpdate(
                item,
                listOf(Supervisor.name)
            ) { batch, supervisor ->
                batch[id] = supervisor.id
                batch[name] = supervisor.name
            }
        }
    }

    override suspend fun update(item: domain.model.Supervisor) {
        newSuspendedTransaction {
            Supervisor.update({ Supervisor.id eq item.id }) {
                it[id] = item.id
                it[name] = item.name
            }
        }
    }
}