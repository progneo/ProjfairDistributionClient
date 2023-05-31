package data.local.dao.base

import data.repository.ParticipationRepositoryImpl
import domain.model.*
import domain.model.base.Entity
import domain.model.base.StringIdEntity
import domain.repository.LoggingRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.asRealmObject
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.query.max
import io.realm.kotlin.types.RealmAny
import kotlinx.coroutines.flow.Flow
import java.lang.IllegalArgumentException
import java.util.UUID


abstract class Dao<T : Entity>(val realm: Realm) {

    inline fun <reified R : Entity> getAll(): Flow<ResultsChange<R>> {
        return realm.query(R::class).asFlow()
    }

    fun insert(item: T) {
        realm.writeBlocking {
            copyToRealm(item, UpdatePolicy.ALL)
        }
    }

    fun insert(items: List<T>) {
        realm.writeBlocking {
            items.forEach {
                copyToRealm(it, UpdatePolicy.ALL)
            }
        }
    }

    inline fun <reified R : Entity> getById(id: Int): R {
        return realm.query<R>("id", id).find().first()
    }

    protected inline fun <reified R : Entity> updateItem(
        item: Project,
    ): Project {
        realm.writeBlocking {
            val project: Project = this.query<Project>("id == $0", item.id).find().first()
            val supervisors = realmListOf<Supervisor>()
            val projectSpecialties = realmListOf<ProjectSpecialty>()

            item.supervisors.forEach { supervisor ->
                val realmSupervisor = realm.query<Supervisor>("id = ${supervisor.id}").find().first()
                supervisors.add(
                    findLatest(realmSupervisor)!!
                )
            }

            var lastId = this.query<ProjectSpecialty>().max<Int>("id").find()!! + 1

            item.projectSpecialties.forEach { projectSpecialty ->
                val realmSpecialty = realm.query<Specialty>("id = ${projectSpecialty.specialty!!.id}").find().first()
                projectSpecialties.add(
                    ProjectSpecialty(
                        id = lastId,
                        course = projectSpecialty.course,
                        specialty = findLatest(realmSpecialty),
                        priority = projectSpecialty.priority
                    )
                )
                lastId++
            }

            val realmDepartment = realm.query<Department>("id = ${item.department!!.id}").find().first()

            project.name = item.name
            project.places = item.places
            project.freePlaces = item.freePlaces
            project.goal = item.goal
            project.difficulty = item.difficulty
            project.dateStart = item.dateStart
            project.dateEnd = item.dateEnd
            project.customer = item.customer
            project.productResult = item.productResult
            project.studyResult = item.studyResult
            project.department = findLatest(realmDepartment)
            project.supervisors = supervisors
            project.projectSpecialties = projectSpecialties
        }

        return realm.query<Project>("id == ${item.id}").find().first()
    }

    inline fun <reified R : Entity> delete(item: T) {
        realm.writeBlocking {
            delete(this.query(R::class, "id == ${item.id}").find().first())
        }
    }

    inline fun <reified R : Entity> deleteAll() {
        realm.writeBlocking {
            delete(this.query(R::class))
        }
    }
}

abstract class LoggingDao(val realm: Realm) {

    inline fun <reified R : StringIdEntity> getAll(): Flow<ResultsChange<R>> {
        return realm.query(R::class).asFlow()
    }

    suspend fun insert(item: Log, logType: LogType, logSource: LogSource) {
        realm.writeBlocking {
            val lastLogType =
                when (logType) {
                    LogType.SAVE -> {
                        LogTypeRealm(
                            id = 0,
                            logType = LogType.SAVE.name
                        )
                    }

                    LogType.REMOVE -> {
                        LogTypeRealm(
                            id = 1,
                            logType = LogType.REMOVE.name
                        )
                    }

                    LogType.CHANGE -> {
                        LogTypeRealm(
                            id = 2,
                            logType = LogType.CHANGE.name
                        )
                    }
                }

            val lastLogSource =
                when (logSource) {
                    LogSource.USER -> {
                        LogSourceRealm(
                            id = 0,
                            logSource = LogSource.USER.name
                        )
                    }

                    LogSource.SERVER -> {
                        LogSourceRealm(
                            id = 1,
                            logSource = LogSource.SERVER.name
                        )
                    }
                }

            val newItem = item.apply {
                type = lastLogType
                source = lastLogSource
            }

            if (item.project != null) {
                newItem.project = findLatest(realm.query<Project>("id == ${item.project!!.id}").find().first())
            } else if (item.student != null) {
                newItem.student = findLatest(realm.query<Student>("id == ${item.student!!.id}").find().first())
            } else if (item.participation != null) {
                newItem.participation = findLatest(realm.query<Participation>("id = ${item.participation!!.id}").find().first())
            }

            copyToRealm(newItem, UpdatePolicy.ALL)
        }
    }

    suspend inline fun deleteAll() {
        realm.writeBlocking {
            delete(this.query(Log::class))
        }
    }
}