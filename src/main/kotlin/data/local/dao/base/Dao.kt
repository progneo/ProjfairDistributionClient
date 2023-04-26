package data.local.dao.base

import domain.model.Project
import domain.model.ProjectSpecialty
import domain.model.Supervisor
import domain.model.base.Entity
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.query.max
import kotlinx.coroutines.flow.Flow


abstract class Dao<T : Entity>(val realm: Realm) {

    inline fun <reified R : Entity> getAll(): Flow<ResultsChange<R>> {
        return realm.query(R::class).asFlow()
    }

    suspend fun insert(item: T) {
        realm.writeBlocking {
            copyToRealm(item, UpdatePolicy.ALL)
        }
    }

    suspend fun insert(items: List<T>) {
        items.forEach {
            insert(it)
        }
    }

    protected suspend inline fun <reified R : Entity> updateItem(
        item: Project,
    ) {
        realm.writeBlocking {
            val project: Project? = this.query<Project>("id == $0", item.id).first().find()
            val supervisors = realmListOf<Supervisor>()
            val projectSpecialties = realmListOf<ProjectSpecialty>()

            item.supervisors.forEach { s ->
                supervisors.add(
                    findLatest(s)!!
                )
            }

            var lastId = this.query<ProjectSpecialty>().max<Int>("id").find()!! + 1

            item.projectSpecialties.forEach { sp ->
                projectSpecialties.add(
                    ProjectSpecialty(
                        id = lastId,
                        course = sp.course,
                        specialty = findLatest(sp.specialty!!),
                        priority = sp.priority
                    )
                )
                lastId++
            }

            project?.name = item.name
            project?.places = item.places
            project?.freePlaces = item.freePlaces
            project?.goal = item.goal
            project?.difficulty = item.difficulty
            project?.dateStart = item.dateStart
            project?.dateEnd = item.dateEnd
            project?.customer = item.customer
            project?.productResult = item.productResult
            project?.studyResult = item.studyResult
            project?.department = findLatest(item.department!!)
            project?.supervisors = supervisors
            project?.projectSpecialties = projectSpecialties
        }
    }

    suspend inline fun <reified R : Entity> delete(item: T) {
        realm.writeBlocking {
            delete(this.query(R::class, "id == ${item.id}").find().first())
        }
    }

    suspend inline fun <reified R : Entity> deleteAll() {
        realm.writeBlocking {
            delete(this.query(R::class))
        }
    }
}