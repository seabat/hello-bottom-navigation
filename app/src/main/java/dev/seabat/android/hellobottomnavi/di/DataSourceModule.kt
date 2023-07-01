package dev.seabat.android.hellobottomnavi.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.seabat.android.hellobottomnavi.data.datasource.github.GithubApiEndpoint
import dev.seabat.android.hellobottomnavi.data.datasource.github.GithubRetrofitClient

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModuleModuleProvider {
    @Provides
    fun provideGithubApiEndpoint(
    ): GithubApiEndpoint {
        return GithubRetrofitClient.createApiEndpoint()
    }
}
