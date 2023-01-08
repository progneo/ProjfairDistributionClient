package data.local.dao

import data.local.dao.base.Dao
import data.local.entity.Project
import data.local.entity.ProjectSpeciality
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update

object ProjectSpecialityDao: Dao<domain.model.ProjectSpeciality>(ProjectSpeciality) {

    override suspend fun getAll(): List<domain.model.ProjectSpeciality> {
        return newSuspendedTransaction {
            val projectSpecialities = mutableListOf<domain.model.ProjectSpeciality>()
            ProjectSpeciality.selectAll().forEach {
                projectSpecialities.add(
                    domain.model.ProjectSpeciality(
                        id = it[ProjectSpeciality.id],
                        projectId = it[ProjectSpeciality.projectId],
                        specialityId = it[ProjectSpeciality.specialityId]
                    )
                )
            }
            projectSpecialities
        }
    }

    override suspend fun insert(item: domain.model.ProjectSpeciality) {
        newSuspendedTransaction {
            ProjectSpeciality.insert {
                it[id] = item.id
                it[projectId] = item.projectId
                it[specialityId] = item.specialityId
            }
        }
    }

    override suspend fun update(item: domain.model.ProjectSpeciality) {
        newSuspendedTransaction {
            Project.update({ ProjectSpeciality.id eq item.id }) {
                it[ProjectSpeciality.id] = item.id
                it[ProjectSpeciality.projectId] = item.projectId
                it[ProjectSpeciality.specialityId] = item.specialityId
            }
        }
    }
}