package dev.seabat.android.hellobottomnavi.domain.usecase

import dev.seabat.android.hellobottomnavi.domain.entity.QiitaArticleListEntity

interface FetchQiitaArticlesUseCaseContract {
    suspend operator fun invoke(
        startCreatedAt: String,
        title: String? = null
    ): QiitaArticleListEntity?
}