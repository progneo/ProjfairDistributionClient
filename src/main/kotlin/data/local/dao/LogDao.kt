package data.local.dao

import data.local.dao.base.Dao
import data.local.dao.base.LoggingDao
import di.Preview
import domain.model.Department
import domain.model.Log
import io.realm.kotlin.Realm
import javax.inject.Inject

class LogDao @Inject constructor(
    @Preview realm: Realm
): LoggingDao(realm)