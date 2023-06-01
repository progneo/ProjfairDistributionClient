package di

import dagger.Module
import dagger.Provides
import data.local.dao.StudentDao
import domain.model.*
import domain.model.base.Entity
import domain.model.base.StringIdEntity
import io.realm.kotlin.Configuration
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Named
import javax.inject.Qualifier

@Module
interface DatabaseModule {

    companion object {

        @Preview
        @AppScope
        @Provides
        fun providePreviewDatabase(): Realm {
            val configuration = RealmConfiguration.Builder(
                schema = setOf(
                    Entity::class,
                    StringIdEntity::class,
                    LoggingEntity::class,
                    Student::class,
                    Supervisor::class,
                    Project::class,
                    Specialty::class,
                    Participation::class,
                    ProjectSpecialty::class,
                    ProjectSupervisor::class,
                    Institute::class,
                    Department::class,
                    SupervisorRole::class,
                    LogTypeRealm::class,
                    LogSourceRealm::class,
                    Log::class,
                )
            ).name("preview.realm").build()
            return Realm.open(configuration)
        }

        @Review
        @AppScope
        @Provides
        fun provideReviewDatabase(): Realm {
            val configuration = RealmConfiguration.Builder(
                schema = setOf(
                    Entity::class,
                    StringIdEntity::class,
                    LoggingEntity::class,
                    Student::class,
                    Supervisor::class,
                    Project::class,
                    Specialty::class,
                    Participation::class,
                    ProjectSpecialty::class,
                    ProjectSupervisor::class,
                    Institute::class,
                    Department::class,
                    SupervisorRole::class,
                    LogTypeRealm::class,
                    LogSourceRealm::class,
                    Log::class,
                )
            ).name("review.realm").build()

            return Realm.open(configuration)
        }
    }
}

@Qualifier
annotation class Preview

@Qualifier
annotation class Review