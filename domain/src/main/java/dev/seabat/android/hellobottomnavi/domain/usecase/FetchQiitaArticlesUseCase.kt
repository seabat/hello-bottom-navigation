package dev.seabat.android.hellobottomnavi.domain.usecase

import dev.seabat.android.hellobottomnavi.domain.entity.QiitaArticleListEntity
import dev.seabat.android.hellobottomnavi.domain.repository.QiitaArticlesRepositoryContract

class FetchQiitaArticlesUseCase(
    private val qiitaArticlesRepository: QiitaArticlesRepositoryContract
) : FetchQiitaArticlesUseCaseContract {
    override suspend fun invoke(
        startCreatedAt: String,
        endCreatedAt: String?,
        title: String?
    ): QiitaArticleListEntity? {
        // val query = "created:>${createdAt}+title:${title}" 500 エラーとなるので保留
        val query = if (endCreatedAt == null) {
            "created:>=${startCreatedAt}"
        } else {
            "created:>=${startCreatedAt}+created:<=${endCreatedAt}"
        }
        return qiitaArticlesRepository.fetchItems(query = query)
    }
}