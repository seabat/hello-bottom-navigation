package dev.seabat.android.hellobottomnavi.di

import dagger.Binds
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
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModuleBinder {
    @Binds
    @Singleton
    abstract fun bindGithubRepositoryContract(
        gitHubRepository: GithubRepository
    ): GithubRepositoryContract

    @Binds
    @Singleton
    abstract fun provideQiitaArticlesRepositoryContract(
        qiitaArticlesRepository: QiitaArticlesRepository
    ): QiitaArticlesRepositoryContract
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModuleProvider {
    @Provides
    fun provideGithubRepository(
        endpoint: GithubApiService
    ): GithubRepository {
        return GithubRepository(endpoint)
    }

    @Provides
    fun provideQiitaArticlesRepository(
        endpoint: QiitaApiService
    ): QiitaArticlesRepository {
        return QiitaArticlesRepository(endpoint)
    }
}

