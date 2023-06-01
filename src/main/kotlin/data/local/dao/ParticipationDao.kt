package data.local.dao

import data.local.dao.base.Dao
import di.Preview
import di.Review
import domain.model.Participation
import io.realm.kotlin.Realm
import javax.inject.Inject

class PreviewParticipationDao @Inject constructor(
    @Preview realm: Realm
): ParticipationDao(realm)

class ReviewParticipationDao @Inject constructor(
    @Review realm: Realm
): ParticipationDao(realm)

open class ParticipationDao(
    realm: Realm
): Dao<Participation>(realm)