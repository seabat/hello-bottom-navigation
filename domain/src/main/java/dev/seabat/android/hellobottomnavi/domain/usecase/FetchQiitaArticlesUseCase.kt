package dev.seabat.android.hellobottomnavi.domain.usecase

import dev.seabat.android.hellobottomnavi.domain.entity.QiitaArticleListEntity
import dev.seabat.android.hellobottomnavi.domain.repository.QiitaArticlesRepositoryContract

class FetchQiitaArticlesUseCase(
    private val qiitaArticlesRepository: QiitaArticlesRepositoryContract
) : FetchQiitaArticlesUseCaseContract {
    override suspend fun invoke(
        startCreatedAt: String,
        title: String?
    ): QiitaArticleListEntity? {
        // TODO: val query = "created:>${createdAt}+title:${title}" は
        //       "+" をエンコードすると500 エラーとなってしまうので保留
        val query = "created:>=$startCreatedAt"
        return qiitaArticlesRepository.fetchItems(query = query)
    }
}