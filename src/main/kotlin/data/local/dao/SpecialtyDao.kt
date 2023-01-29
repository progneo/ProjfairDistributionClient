package data.local.dao

import data.local.dao.base.Dao
import domain.model.Specialty
import io.realm.kotlin.Realm
import javax.inject.Inject

class SpecialtyDao @Inject constructor(
    realm: Realm
): Dao<Specialty>(realm)