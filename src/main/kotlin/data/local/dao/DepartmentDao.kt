package data.local.dao

import data.local.dao.base.Dao
import domain.model.Department
import io.realm.kotlin.Realm
import javax.inject.Inject

class DepartmentDao @Inject constructor(
    realm: Realm
): Dao<Department>(realm)