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
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

class PreviewParticipationDao @Inject constructor(
    @Preview realm: Realm
): ParticipationDao(realm)

class ReviewParticipationDao @Inject constructor(
    @Review realm: Realm
): ParticipationDao(realm)

open class ParticipationDao(
    realm: Realm
): Dao<Participation>(realm) {

    fun update(item: Participation) {
        realm.writeBlocking {
            val participation: Participation = this.query<Participation>("id == $0", item.id).find().first()

            participation.priority = item.priority
            participation.projectId = item.projectId
            participation.updatedAt = item.updatedAt
        }
    }

    fun update(items: List<Participation>) {
        realm.writeBlocking {
            items.forEach { part ->
                if (part.id == 0) {
                    val newId = query<Participation>().max<Int>("id").find()!! + 1
                    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
                    val currentDate = sdf.format(Date())
                    val newPart = Participation(
                        id = newId,
                        studentId = part.studentId,
                        studentNumz = part.studentNumz,
                        studentName = part.studentName,
                        projectId = part.projectId,
                        priority = part.priority,
                        updatedAt = currentDate,
                        state = 1
                    )
                    copyToRealm(newPart, UpdatePolicy.ALL)
                } else {
                    val participation: Participation = this.query<Participation>("id == $0", part.id).find().first()

                    participation.priority = part.priority
                    participation.projectId = part.projectId
                    participation.updatedAt = part.updatedAt
                }
            }
        }
    }
}