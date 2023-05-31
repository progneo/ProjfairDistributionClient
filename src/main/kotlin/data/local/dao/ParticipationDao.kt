package data.local.dao

import data.local.dao.base.Dao
import di.Preview
import domain.model.Participation
import io.realm.kotlin.Realm
import javax.inject.Inject

class ParticipationDao @Inject constructor(
    @Preview realm: Realm
): Dao<Participation>(realm)