package data.local.dao

import data.local.dao.base.Dao
import data.local.dao.base.batchInsertOnDuplicateKeyUpdate
import data.local.entity.Student
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update

object StudentDao : Dao<domain.model.Student>(Student) {
    override suspend fun getAll(): List<domain.model.Student> {
        return newSuspendedTransaction {
            Student.selectAll().map {
                domain.model.Student(
                    id = it[Student.id],
                    name = it[Student.name],
                    group = it[Student.group],
                    shouldDistribute = it[Student.shouldDistribute],
                    specialtyId = it[Student.specialtyId]
                )
            }
        }
    }

    override suspend fun insert(item: domain.model.Student) {
        newSuspendedTransaction {
            Student.batchInsertOnDuplicateKeyUpdate(
                item,
                listOf(Student.name, Student.group, Student.shouldDistribute, Student.specialtyId)
            ) { batch, student ->
                batch[id] = student.id
                batch[name] = student.name
                batch[group] = student.group
                batch[shouldDistribute] = student.shouldDistribute
                batch[specialtyId] = student.specialtyId
            }
        }

    }

    override suspend fun update(item: domain.model.Student) {
        newSuspendedTransaction {
            Student.update({ Student.id eq item.id }) {
                it[id] = item.id
                it[name] = item.name
                it[group] = item.group
                it[shouldDistribute] = item.shouldDistribute
                it[specialtyId] = item.specialtyId
            }
        }
    }

    fun insertOrUpdate(it: UpdateBuilder<Number>, item: domain.model.Student) {
        it[Student.id] = item.id
        it[Student.name] = item.name
        it[Student.group] = item.group
        it[Student.shouldDistribute] = item.shouldDistribute
        it[Student.specialtyId] = item.specialtyId
    }

    suspend fun markStudentsAsExceptional(studentIds: List<Int>) {
        newSuspendedTransaction {
            for (id in studentIds) {
                Student.update({ Student.id eq id }) {
                    it[shouldDistribute] = false
                }
            }
        }
    }
}