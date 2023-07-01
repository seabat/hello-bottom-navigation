package dev.seabat.android.hellobottomnavi.domain.usecase

import dev.seabat.android.hellobottomnavi.domain.entity.RepositoryListEntity

interface GithubUseCaseContract {
    suspend fun loadRepos(query: String?): RepositoryListEntity?
}