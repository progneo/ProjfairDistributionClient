package data.local.dao

import data.local.dao.base.Dao
import io.realm.kotlin.Realm
import javax.inject.Inject

class StudentDao @Inject constructor(
    realm: Realm
): Dao<domain.model.Student>(realm)