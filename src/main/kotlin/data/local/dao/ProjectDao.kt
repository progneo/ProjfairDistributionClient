package data.local.dao

import data.local.dao.base.Dao
import data.local.dao.base.batchInsertOnDuplicateKeyUpdate
import data.local.entity.Project
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update

object ProjectDao : Dao<domain.model.Project>(Project) {

    override suspend fun getAll(): List<domain.model.Project> {
        return newSuspendedTransaction {
            val projects = mutableListOf<domain.model.Project>()
            Project.selectAll().forEach {
                projects.add(
                    domain.model.Project(
                        id = it[Project.id],
                        createdAt = it[Project.createdAt],
                        updatedAt = it[Project.updatedAt],
                        title = it[Project.title],
                        places = it[Project.places],
                        goal = it[Project.goal],
                        difficulty = it[Project.difficulty],
                        dateStart = it[Project.dateStart],
                        dateEnd = it[Project.dateEnd],
                        customer = it[Project.customer],
                        additionalInf = it[Project.additionalInf],
                        productResult = it[Project.productResult],
                        studyResult = it[Project.studyResult],
                        supervisors = it[Project.supervisors]
                    )
                )
            }
            projects
        }
    }

    override suspend fun insert(item: domain.model.Project) {
        newSuspendedTransaction {
            Project.batchInsertOnDuplicateKeyUpdate(
                item,
                listOf(
                    Project.createdAt,
                    Project.updatedAt,
                    Project.title,
                    Project.places,
                    Project.goal,
                    Project.difficulty,
                    Project.dateStart,
                    Project.dateEnd,
                    Project.customer,
                    Project.additionalInf,
                    Project.productResult,
                    Project.studyResult,
                    Project.supervisors
                )
            ) { batch, project ->
                batch[id] = project.id
                batch[createdAt] = project.createdAt ?: ""
                batch[updatedAt] = project.updatedAt ?: ""
                batch[title] = project.title
                batch[places] = project.places
                batch[goal] = project.goal ?: ""
                batch[difficulty] = project.difficulty
                batch[dateStart] = project.dateStart
                batch[dateEnd] = project.dateEnd
                batch[customer] = project.customer ?: ""
                batch[additionalInf] = project.additionalInf ?: ""
                batch[productResult] = project.productResult
                batch[studyResult] = project.studyResult
                batch[supervisors] = project.supervisors
            }
        }
    }

    override suspend fun update(item: domain.model.Project) {
        newSuspendedTransaction {
            Project.update({ Project.id eq item.id }) {
                it[id] = item.id
                it[createdAt] = item.createdAt ?: ""
                it[updatedAt] = item.updatedAt ?: ""
                it[title] = item.title
                it[places] = item.places
                it[goal] = item.goal ?: ""
                it[difficulty] = item.difficulty
                it[dateStart] = item.dateStart
                it[dateEnd] = item.dateEnd
                it[customer] = item.customer ?: ""
                it[additionalInf] = item.additionalInf ?: ""
                it[productResult] = item.productResult
                it[studyResult] = item.studyResult
                it[supervisors] = item.supervisors
            }
        }
    }
}