package data.local.dao

import data.local.dao.base.Dao
import di.Preview
import di.Review
import domain.model.Project
import io.realm.kotlin.Realm
import javax.inject.Inject

class PreviewProjectDao @Inject constructor(
    @Preview realm: Realm
): ProjectDao(realm)

class ReviewProjectDao @Inject constructor(
    @Review realm: Realm
): ProjectDao(realm)

open class ProjectDao(realm: Realm): Dao<Project>(realm) {
    fun update(item: Project): Project {
        return updateItem<Project>(item)
    }
}