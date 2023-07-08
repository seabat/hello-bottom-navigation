package dev.seabat.android.hellobottomnavi.domain.usecase

import dev.seabat.android.hellobottomnavi.domain.entity.QiitaArticleListEntity
import dev.seabat.android.hellobottomnavi.domain.repository.QiitaArticlesRepositoryContract

class FetchQiitaArticlesUseCase(
    private val qiitaArticlesRepository: QiitaArticlesRepositoryContract
) : FetchQiitaArticlesUseCaseContract {
    override suspend fun invoke(createdAt: String, title: String): QiitaArticleListEntity? {
        // val query = "created:>${createdAt}+title:${title}" 500 エラーとなるので保留
        val query = "created:>${createdAt}"
        return qiitaArticlesRepository.fetchItems(query = query)
    }
}