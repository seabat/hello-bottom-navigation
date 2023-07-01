package dev.seabat.android.hellobottomnavi.data.repository

import dev.seabat.android.hellobottomnavi.data.datasource.github.GitHubExceptionConverter
import dev.seabat.android.hellobottomnavi.data.datasource.github.model.GetAllRepoResponse
import dev.seabat.android.hellobottomnavi.data.datasource.github.GithubApiEndpoint
import dev.seabat.android.hellobottomnavi.data.datasource.github.model.Repository
import dev.seabat.android.hellobottomnavi.domain.entity.OwnerEntity
import dev.seabat.android.hellobottomnavi.domain.entity.RepositoryEntity
import dev.seabat.android.hellobottomnavi.domain.entity.RepositoryListEntity
import dev.seabat.android.hellobottomnavi.domain.exception.HelloException
import dev.seabat.android.hellobottomnavi.domain.repository.GithubRepositoryContract
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Response
import kotlin.coroutines.resumeWithException

class GithubRepository(private val endpoint: GithubApiEndpoint) : GithubRepositoryContract {

    override suspend fun fetchRepos(query: String?): RepositoryListEntity? {
        return suspendCancellableCoroutine<RepositoryListEntity?> { continuation ->
            val call = endpoint.getAllRepo(query ?: "architecture")
            call.enqueue(object : retrofit2.Callback<GetAllRepoResponse> {
                override fun onFailure(call: Call<GetAllRepoResponse>, t: Throwable) {
                    continuation.resumeWithException(HelloException.convertTo(t))
                }
                override fun onResponse(
                    call: Call<GetAllRepoResponse>,
                    response: Response<GetAllRepoResponse>
                ) {
                    if(response.isSuccessful) {
                        val getAllRepoResponse = response.body()
                        val entityList = convertToEntity(getAllRepoResponse?.items)
                        continuation.resume(entityList, null)
                    } else {
                        continuation.resumeWithException(GitHubExceptionConverter.convertTo(
                            response.code(),
                            response.errorBody()?.string()
                        ))
                    }
                }
            })
        }
    }

    private fun convertToEntity(repos: ArrayList<Repository>?): RepositoryListEntity? {
        return repos?.let { repos ->
            RepositoryListEntity(
                repos.map {
                    RepositoryEntity(
                        name = it.name,
                        description = it.description,
                        created_at = it.created_at,
                        owner = OwnerEntity( avatar_url = it.owner.avatar_url)
                    )
                } as ArrayList<RepositoryEntity>
            )
        } ?: null
    }
}