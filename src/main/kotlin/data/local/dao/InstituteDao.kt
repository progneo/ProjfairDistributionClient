package data.local.dao

import data.local.dao.base.Dao
import di.Preview
import domain.model.Institute
import io.realm.kotlin.Realm
import javax.inject.Inject

class InstituteDao @Inject constructor(
    @Preview realm: Realm
): Dao<Institute>(realm)