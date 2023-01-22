package data.local.dao

import data.local.dao.base.Dao
import data.local.entity.Project
import data.local.entity.ProjectSpecialty
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update

object ProjectSpecialityDao: Dao<domain.model.ProjectSpeciality>(ProjectSpecialty) {

    override suspend fun getAll(): List<domain.model.ProjectSpeciality> {
        return newSuspendedTransaction {
            val projectSpecialities = mutableListOf<domain.model.ProjectSpeciality>()
            ProjectSpecialty.selectAll().forEach {
                projectSpecialities.add(
                    domain.model.ProjectSpeciality(
                        id = it[ProjectSpecialty.id],
                        projectId = it[ProjectSpecialty.projectId],
                        specialityId = it[ProjectSpecialty.specialityId]
                    )
                )
            }
            projectSpecialities
        }
    }

    override suspend fun insert(item: domain.model.ProjectSpeciality) {
        newSuspendedTransaction {
            ProjectSpecialty.insert {
                it[id] = item.id
                it[projectId] = item.projectId
                it[specialityId] = item.specialityId
            }
        }
    }

    override suspend fun update(item: domain.model.ProjectSpeciality) {
        newSuspendedTransaction {
            Project.update({ ProjectSpecialty.id eq item.id }) {
                it[ProjectSpecialty.id] = item.id
                it[ProjectSpecialty.projectId] = item.projectId
                it[ProjectSpecialty.specialityId] = item.specialityId
            }
        }
    }
}