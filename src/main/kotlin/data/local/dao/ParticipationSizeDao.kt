package data.local.dao

import data.local.dao.base.Dao
import di.Preview
import di.Review
import domain.model.*
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.query.max
import javax.inject.Inject

open class ParticipationSizeDao @Inject constructor(
    @Review realm: Realm
): Dao<ParticipationSize>(realm)