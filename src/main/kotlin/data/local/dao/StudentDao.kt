package data.local.dao

import data.local.dao.base.Dao
import data.local.entity.Student
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update

object StudentDao: Dao<domain.model.Student>(Student) {
    override suspend fun getAll(): List<domain.model.Student> {
        return newSuspendedTransaction {
            val students = mutableListOf<domain.model.Student>()
            Student.selectAll().forEach {
                students.add(
                    domain.model.Student(
                        id = it[Student.id],
                        name = it[Student.name],
                        group =  it[Student.group],
                        shouldDistribute = it[Student.shouldDistribute],
                        specialityId = it[Student.specialityId]
                    )
                )
            }
            students
        }
    }

    override suspend fun insert(item: domain.model.Student) {
        newSuspendedTransaction {
            Student.insert {
                it[id] = item.id
                it[name] = item.name
                it[group] = item.group
                it[shouldDistribute] = item.shouldDistribute
                it[specialityId] = item.specialityId
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
                it[specialityId] = item.specialityId
            }
        }
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