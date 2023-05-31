package data.local.dao

import data.local.dao.base.Dao
import di.Preview
import domain.model.Supervisor
import io.realm.kotlin.Realm
import javax.inject.Inject

class SupervisorDao @Inject constructor(
    @Preview realm: Realm
): Dao<Supervisor>(realm)