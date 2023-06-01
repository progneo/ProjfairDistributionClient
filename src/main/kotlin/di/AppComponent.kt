package di

import dagger.Component
import ui.details.participation.di.PreviewParticipationDetailsComponent
import ui.details.participation.di.ReviewParticipationDetailsComponent
import ui.details.project.di.ProjectDetailsComponent
import ui.distribution_algorithm.di.AlgorithmComponent
import ui.preview.di.PreviewComponent
import ui.review.di.ReviewComponent
import ui.uploaddata.di.UploadPreviewDataComponent
import ui.uploaddata.di.UploadReviewDataComponent
import javax.inject.Scope

@Scope
annotation class AppScope

@[AppScope Component(
    modules = [
        RepositoryModule::class,
        UseCaseModule::class,
        CoroutineDispatcherModule::class,
        DatabaseModule::class,
        NetworkModule::class,
        InteractorModule::class,
        ViewModelModule::class,
        DaoModule::class
    ]
)]
interface AppComponent {

    @Component.Factory
    interface Factory {

        fun create(): AppComponent
    }

    fun inject(uploadPreviewDataComponent: UploadPreviewDataComponent)
    fun inject(uploadReviewDataComponent: UploadReviewDataComponent)
    fun inject(previewComponent: PreviewComponent)
    fun inject(projectDetailsComponent: ProjectDetailsComponent)
    fun inject(previewParticipationDetailsComponent: PreviewParticipationDetailsComponent)
    fun inject(reviewParticipationDetailsComponent: ReviewParticipationDetailsComponent)
    fun inject(algorithmComponent: AlgorithmComponent)
    fun inject(reviewComponent: ReviewComponent)
}