package data.local.dao

import data.local.dao.base.Dao
import domain.model.Project
import io.realm.kotlin.Realm
import javax.inject.Inject

class ProjectDao @Inject constructor(
    realm: Realm
): Dao<Project>(realm) {

    suspend fun update(item: Project): Project {
        return updateItem<Project>(item)
    }
}