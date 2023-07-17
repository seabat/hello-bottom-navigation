package dev.seabat.android.hellobottomnavi.domain.entity

import java.util.Date

data class QiitaArticleEntity(
    val totalCount: Int?,
    val createdAt: Date,
    val title: String,
    val url: String
)
