package di

import dagger.Module
import dagger.Provides
import data.local.dao.*
import domain.model.*
import domain.model.base.Entity
import domain.model.base.StringIdEntity
import io.realm.kotlin.Configuration
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Named
import javax.inject.Qualifier

@Module
interface DaoModule {

    companion object {

        @Preview
        @AppScope
        @Provides
        fun provideProjectPreviewDao(@Preview realm: Realm): ProjectDao {
            return PreviewProjectDao(realm)
        }

        @Review
        @AppScope
        @Provides
        fun provideProjectReviewDao(@Review realm: Realm): ProjectDao {
            return ReviewProjectDao(realm)
        }

        @Preview
        @AppScope
        @Provides
        fun provideStudentPreviewDao(@Preview realm: Realm): StudentDao {
            return PreviewStudentDao(realm)
        }

        @Review
        @AppScope
        @Provides
        fun provideStudentReviewDao(@Review realm: Realm): StudentDao {
            return ReviewStudentDao(realm)
        }

        @Preview
        @AppScope
        @Provides
        fun provideParticipationPreviewDao(@Preview realm: Realm): ParticipationDao {
            return PreviewParticipationDao(realm)
        }

        @Review
        @AppScope
        @Provides
        fun provideParticipationReviewDao(@Review realm: Realm): ParticipationDao {
            return ReviewParticipationDao(realm)
        }
    }
}