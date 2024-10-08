package dev.seabat.android.hellobottomnavi.data.repository

import dev.seabat.android.hellobottomnavi.data.datasource.github.GithubApiService
import dev.seabat.android.hellobottomnavi.data.datasource.github.GithubExceptionConverter
import dev.seabat.android.hellobottomnavi.data.datasource.github.model.Repository
import dev.seabat.android.hellobottomnavi.domain.entity.OwnerEntity
import dev.seabat.android.hellobottomnavi.domain.entity.RepositoryEntity
import dev.seabat.android.hellobottomnavi.domain.entity.RepositoryListEntity
import dev.seabat.android.hellobottomnavi.domain.exception.HelloException
import dev.seabat.android.hellobottomnavi.domain.repository.GithubRepositoryContract
import java.lang.Exception
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GithubRepository(private val endpoint: GithubApiService) : GithubRepositoryContract {

    override suspend fun fetchRepos(query: String?): RepositoryListEntity? {
        // NOTE: 同期方式の場合はメインスレッド以外で通信する必要あり
        return withContext(Dispatchers.IO) {
            val response = try {
                // 同期方式で HTTP 通信を行う
                endpoint.getAllRepo(query ?: "architecture").execute()
            } catch (e: Exception) { // 通信自体が失敗した場合
                val exception = HelloException.convertTo(e as Throwable)
                throw exception
            }

            if (response.isSuccessful) {
                val getAllRepoResponse = response.body()
                val entityList = convertToEntity(getAllRepoResponse?.items)
                entityList
            } else {
                val exception = GithubExceptionConverter.convertTo(
                    response.code(),
                    response.errorBody()?.string()
                )
                throw exception
            }
        }
    }

    private fun convertToEntity(repos: List<Repository>?): RepositoryListEntity? =
        repos?.let {
            RepositoryListEntity(
                it.map { repo ->
                    RepositoryEntity(
                        name = repo.name,
                        fullName = repo.fullName,
                        htmlUrl = repo.htmlUrl,
                        description = repo.description,
                        createdAt = repo.createdAt,
                        owner = OwnerEntity(avatarUrl = repo.owner.avatarUrl)
                    )
                } as ArrayList<RepositoryEntity>
            )
        }
}