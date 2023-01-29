package data.local.dao

import data.local.dao.base.Dao
import domain.model.ProjectSupervisor
import io.realm.kotlin.Realm
import javax.inject.Inject

class ProjectSupervisorDao @Inject constructor(
    realm: Realm
): Dao<ProjectSupervisor>(realm)