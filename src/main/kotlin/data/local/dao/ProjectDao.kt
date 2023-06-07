package data.local.dao

import data.local.dao.base.Dao
import di.Preview
import di.Review
import domain.model.*
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.query.max
import javax.inject.Inject

class PreviewProjectDao @Inject constructor(
    @Preview realm: Realm
): ProjectDao(realm)

class ReviewProjectDao @Inject constructor(
    @Review realm: Realm
): ProjectDao(realm)

open class ProjectDao(realm: Realm): Dao<Project>(realm) {
    fun update(item: Project): Project {
        realm.writeBlocking {
            val project: Project = this.query<Project>("id == $0", item.id).find().first()
            val supervisors = realmListOf<Supervisor>()
            val projectSpecialties = realmListOf<ProjectSpecialty>()

            item.supervisors.forEach { supervisor ->
                val realmSupervisor = realm.query<Supervisor>("id = ${supervisor.id}").find().first()
                supervisors.add(
                    findLatest(realmSupervisor)!!
                )
            }

            var lastId = this.query<ProjectSpecialty>().max<Int>("id").find()!! + 1

            item.projectSpecialties.forEach { projectSpecialty ->
                val realmSpecialty = realm.query<Specialty>("id = ${projectSpecialty.specialty!!.id}").find().first()
                projectSpecialties.add(
                    ProjectSpecialty(
                        id = lastId,
                        course = projectSpecialty.course,
                        specialty = findLatest(realmSpecialty),
                        priority = projectSpecialty.priority
                    )
                )
                lastId++
            }

            val realmDepartment = realm.query<Department>("id = ${item.department!!.id}").find().first()

            project.name = item.name
            project.places = item.places
            project.freePlaces = item.freePlaces
            project.goal = item.goal
            project.difficulty = item.difficulty
            project.dateStart = item.dateStart
            project.dateEnd = item.dateEnd
            project.customer = item.customer
            project.productResult = item.productResult
            project.studyResult = item.studyResult
            project.department = findLatest(realmDepartment)
            project.supervisors = supervisors
            project.projectSpecialties = projectSpecialties
        }

        return realm.query<Project>("id == ${item.id}").find().first()
    }
}