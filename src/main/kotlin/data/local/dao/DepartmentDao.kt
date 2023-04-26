package data.local.dao

import data.local.dao.base.Dao
import domain.model.Department
import io.realm.kotlin.Realm
import javax.inject.Inject

class DepartmentDao @Inject constructor(
    realm: Realm
): Dao<Department>(realm) {

//    override suspend fun insert(item: Department) {
//        realm.writeBlocking {
//            val new = findLatest(item)?.apply {
//                name = item.name
//                institute = item.institute
//            } as Department
//            copyToRealm(new, UpdatePolicy.ALL)
//        }
//    }
}