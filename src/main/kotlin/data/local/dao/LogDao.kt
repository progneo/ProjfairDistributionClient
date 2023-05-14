package data.local.dao

import data.local.dao.base.Dao
import domain.model.Department
import domain.model.Log
import io.realm.kotlin.Realm
import javax.inject.Inject

class LogDao @Inject constructor(
    realm: Realm
): Dao<Log>(realm) {

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