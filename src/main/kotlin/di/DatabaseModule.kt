package di

import dagger.Module
import dagger.Provides
import data.local.dao.StudentDao
import domain.model.Department
import domain.model.*
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

@Module
interface DatabaseModule {

    companion object {

        @AppScope
        @Provides
        fun provideDatabase(): Realm {
            val configuration = RealmConfiguration.create(
                schema = setOf(
                    Student::class,
                    Supervisor::class,
                    Project::class,
                    Specialty::class,
                    Participation::class,
                    ProjectSpecialty::class,
                    ProjectSupervisor::class,
                    Institute::class,
                    Department::class
                )
            )
            return Realm.open(configuration)
        }

        @AppScope
        @Provides
        fun provideStudentDao(realm: Realm): StudentDao {
            return StudentDao(realm)
        }
    }
}