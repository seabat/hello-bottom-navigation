package dev.seabat.android.hellobottomnavi.domain.repository

import dev.seabat.android.hellobottomnavi.domain.entity.RepositoryListEntity

interface GithubRepositoryContract {
    suspend fun fetchRepos(query: String?): RepositoryListEntity?
}