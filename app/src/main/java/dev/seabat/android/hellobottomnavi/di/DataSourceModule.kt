package dev.seabat.android.hellobottomnavi.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.seabat.android.hellobottomnavi.data.datasource.github.GithubApiService
import dev.seabat.android.hellobottomnavi.data.datasource.github.GithubApi
import dev.seabat.android.hellobottomnavi.data.datasource.qiita.QiitaApi
import dev.seabat.android.hellobottomnavi.data.datasource.qiita.QiitaApiService

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModuleModuleProvider {
    @Provides
    fun provideGithubApiEndpoint(
    ): GithubApiService {
        return GithubApi.githubApiService
    }

    @Provides
    fun provideQiitaApiEndpoint(
    ): QiitaApiService {
        return QiitaApi.qiitaApiService
    }
}
