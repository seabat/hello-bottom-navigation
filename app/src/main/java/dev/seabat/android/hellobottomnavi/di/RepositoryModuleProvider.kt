package dev.seabat.android.hellobottomnavi.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.seabat.android.hellobottomnavi.data.datasource.github.GithubApiService
import dev.seabat.android.hellobottomnavi.data.datasource.qiita.QiitaApiService
import dev.seabat.android.hellobottomnavi.data.repository.GithubRepository
import dev.seabat.android.hellobottomnavi.data.repository.QiitaArticlesRepository
import dev.seabat.android.hellobottomnavi.domain.repository.GithubRepositoryContract
import dev.seabat.android.hellobottomnavi.domain.repository.QiitaArticlesRepositoryContract
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GithubRepositoryQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class QiitaArticlesRepositoryQualifier

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModuleProvider {
    @GithubRepositoryQualifier
    @Provides
    @Singleton
    fun provideGithubRepository(
        endpoint: GithubApiService
    ): GithubRepositoryContract = GithubRepository(endpoint)

    @QiitaArticlesRepositoryQualifier
    @Provides
    @Singleton
    fun provideQiitaArticlesRepository(
        endpoint: QiitaApiService
    ): QiitaArticlesRepositoryContract = QiitaArticlesRepository(endpoint)
}
