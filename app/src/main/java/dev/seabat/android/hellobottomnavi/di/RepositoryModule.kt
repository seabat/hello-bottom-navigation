package dev.seabat.android.hellobottomnavi.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.seabat.android.hellobottomnavi.data.datasource.github.GithubApiEndpoint
import dev.seabat.android.hellobottomnavi.data.repository.GithubRepository
import dev.seabat.android.hellobottomnavi.domain.repository.GithubRepositoryContract
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModuleBinder {
    @Binds
    @Singleton
    abstract fun bindGithubRepositoryContract(
        gitHubRepository: GithubRepository
    ): GithubRepositoryContract
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModuleProvider {
    @Provides
    fun provideGithubRepository(
        endpoint: GithubApiEndpoint
    ): GithubRepository {
        return GithubRepository(endpoint)
    }
}

