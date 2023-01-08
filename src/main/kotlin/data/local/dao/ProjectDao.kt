package data.local.dao

import data.local.dao.base.Dao
import data.local.entity.Project
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update

object ProjectDao: Dao<domain.model.Project>(Project) {

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
            Project.insert {
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