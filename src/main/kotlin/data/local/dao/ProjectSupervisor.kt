package data.local.dao

import data.local.entity.ProjectSupervisor
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update

object ProjectSupervisor : Dao<domain.model.ProjectSupervisor>(ProjectSupervisor) {

    override suspend fun getAll(): List<domain.model.ProjectSupervisor> {
        return newSuspendedTransaction {
            val projectSupervisors = mutableListOf<domain.model.ProjectSupervisor>()
            ProjectSupervisor.selectAll().forEach {
                projectSupervisors.add(
                    domain.model.ProjectSupervisor(
                        id = it[ProjectSupervisor.id],
                        projectId = it[ProjectSupervisor.projectId],
                        supervisorId = it[ProjectSupervisor.supervisorId]
                    )
                )
            }
            projectSupervisors
        }
    }

    override suspend fun insert(item: domain.model.ProjectSupervisor) {
        newSuspendedTransaction {
            ProjectSupervisor.insert {
                it[id] = item.id
                it[projectId] = item.projectId
                it[supervisorId] = item.supervisorId
            }
        }
    }

    override suspend fun update(item: domain.model.ProjectSupervisor) {
        newSuspendedTransaction {
            ProjectSupervisor.update({ ProjectSupervisor.id eq item.id }) {
                it[id] = item.id
                it[projectId] = item.projectId
                it[supervisorId] = item.supervisorId
            }
        }
    }
}