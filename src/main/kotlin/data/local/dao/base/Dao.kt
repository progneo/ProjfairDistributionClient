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
        return realm.query<R>("id == $0", id).find().first()
    }

    inline fun <reified R : Entity> delete(item: T) {
        realm.writeBlocking {
            delete(this.query(R::class, "id == ${item.id}").find().first())
        }
    }

    inline fun <reified R : Entity> delete(items: List<T>) {
        realm.writeBlocking {
            items.forEach {
                delete(this.query(R::class, "id == ${it.id}").find().first())
            }
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