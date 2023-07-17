package dev.seabat.android.hellobottomnavi.domain.repository

import dev.seabat.android.hellobottomnavi.domain.entity.QiitaArticleListEntity

interface QiitaArticlesRepositoryContract {
    suspend fun fetchItems(query: String?): QiitaArticleListEntity?
}