package data.local.dao

import data.local.dao.base.Dao
import di.Preview
import domain.model.ProjectSpecialty
import io.realm.kotlin.Realm
import javax.inject.Inject

class ProjectSpecialityDao @Inject constructor(
    @Preview realm: Realm
): Dao<ProjectSpecialty>(realm)