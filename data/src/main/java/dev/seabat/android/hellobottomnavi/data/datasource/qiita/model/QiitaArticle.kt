package dev.seabat.android.hellobottomnavi.data.datasource.qiita.model

import com.squareup.moshi.Json
import java.util.Date

data class QiitaArticle(
    @Json(name = "created_at")val createdAt: String,
    val title: String,
    val url: String
)