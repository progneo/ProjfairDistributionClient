package data.local.dao

import data.local.dao.base.Dao
import domain.model.Participation
import io.realm.kotlin.Realm
import javax.inject.Inject

class ParticipationDao @Inject constructor(
    realm: Realm
): Dao<Participation>(realm)