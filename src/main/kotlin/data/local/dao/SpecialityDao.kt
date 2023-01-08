package data.local.dao

import data.local.dao.base.Dao
import data.local.entity.Speciality
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update

object SpecialityDao: Dao<domain.model.Speciality>(Speciality) {

    override suspend fun getAll(): List<domain.model.Speciality> {
        return newSuspendedTransaction {
            val specialities = mutableListOf<domain.model.Speciality>()
            Speciality.selectAll().forEach {
                specialities.add(
                    domain.model.Speciality(
                        id = it[Speciality.id],
                        name = it[Speciality.name]
                    )
                )
            }
            specialities
        }
    }

    override suspend fun insert(item: domain.model.Speciality) {
        newSuspendedTransaction {
            Speciality.insert {
                it[id] = item.id
                it[name] = item.name
            }
        }
    }

    override suspend fun update(item: domain.model.Speciality) {
        newSuspendedTransaction {
            Speciality.update({ Speciality.id eq item.id }) {
                it[id] = item.id
                it[name] = item.name
            }
        }
    }
}