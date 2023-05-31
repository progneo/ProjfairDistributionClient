package data.local.dao

import data.local.dao.base.Dao
import di.Preview
import domain.model.Specialty
import io.realm.kotlin.Realm
import javax.inject.Inject

class SpecialtyDao @Inject constructor(
    @Preview realm: Realm
): Dao<Specialty>(realm)