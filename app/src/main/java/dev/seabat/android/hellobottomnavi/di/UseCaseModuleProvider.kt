package dev.seabat.android.hellobottomnavi.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.seabat.android.hellobottomnavi.domain.repository.GithubRepositoryContract
import dev.seabat.android.hellobottomnavi.domain.repository.QiitaArticlesRepositoryContract
import dev.seabat.android.hellobottomnavi.domain.usecase.FetchQiitaArticlesUseCase
import dev.seabat.android.hellobottomnavi.domain.usecase.FetchQiitaArticlesUseCaseContract
import dev.seabat.android.hellobottomnavi.domain.usecase.GithubUseCase
import dev.seabat.android.hellobottomnavi.domain.usecase.GithubUseCaseContract
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GithubUseCaseQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FetchQiitaArticlesUseCaseQualifier

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModuleProvider {
    @GithubUseCaseQualifier
    @Provides
    fun provideGithubUseCase(
        @GithubRepositoryQualifier githubRepository: GithubRepositoryContract
    ): GithubUseCaseContract = GithubUseCase(githubRepository)

    @FetchQiitaArticlesUseCaseQualifier
    @Provides
    fun provideFetchQiitaArticlesUseCase(
        @QiitaArticlesRepositoryQualifier qiitaArticlesRepository: QiitaArticlesRepositoryContract
    ): FetchQiitaArticlesUseCaseContract = FetchQiitaArticlesUseCase(qiitaArticlesRepository)
}
