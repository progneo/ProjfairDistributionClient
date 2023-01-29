package data.local.dao

import data.local.dao.base.Dao
import domain.model.ProjectSpecialty
import io.realm.kotlin.Realm
import javax.inject.Inject

class ProjectSpecialityDao @Inject constructor(
    realm: Realm
): Dao<ProjectSpecialty>(realm)