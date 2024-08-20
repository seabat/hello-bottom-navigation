package dev.seabat.android.hellobottomnavi.domain.usecase

import dev.seabat.android.hellobottomnavi.domain.entity.RepositoryListEntity
import dev.seabat.android.hellobottomnavi.domain.repository.GithubRepositoryContract

class GithubUseCase(val githubRepository: GithubRepositoryContract) : GithubUseCaseContract {
    override suspend fun loadRepos(
        query: String?
    ): RepositoryListEntity? = githubRepository.fetchRepos(query)
}