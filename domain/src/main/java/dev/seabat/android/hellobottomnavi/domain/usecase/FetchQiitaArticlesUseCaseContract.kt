package dev.seabat.android.hellobottomnavi.domain.usecase

import dev.seabat.android.hellobottomnavi.domain.entity.QiitaArticleListEntity

interface FetchQiitaArticlesUseCaseContract {
    suspend operator fun invoke(
        createdAt: String, title: String
    ): QiitaArticleListEntity?
}