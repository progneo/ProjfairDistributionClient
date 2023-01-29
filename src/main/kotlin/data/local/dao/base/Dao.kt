package data.local.dao.base

import domain.model.base.Entity
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow


abstract class Dao<T: Entity>(val realm: Realm) {

    inline fun <reified R: Entity> getAll(): Flow<ResultsChange<R>> {
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

    suspend fun update(item: T) {
        insert(item)
    }

    suspend inline fun <reified R: Entity> delete(item: T) {
        realm.writeBlocking {
            delete(this.query(R::class, "id == ${item.id}").find().first())
        }
    }

    suspend inline fun <reified R: Entity> deleteAll() {
        realm.writeBlocking {
            delete(this.query(R::class))
        }
    }
}