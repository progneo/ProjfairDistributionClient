package data.local.dao

import data.local.dao.base.Dao
import di.Preview
import domain.model.ProjectSupervisor
import io.realm.kotlin.Realm
import javax.inject.Inject

class ProjectSupervisorDao @Inject constructor(
    @Preview realm: Realm
): Dao<ProjectSupervisor>(realm)