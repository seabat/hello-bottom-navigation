package dev.seabat.android.hellobottomnavi.data.repository

import dev.seabat.android.hellobottomnavi.data.BuildConfig
import dev.seabat.android.hellobottomnavi.data.datasource.qiita.QiitaApiService
import dev.seabat.android.hellobottomnavi.data.datasource.qiita.QiitaExceptionConverter
import dev.seabat.android.hellobottomnavi.data.datasource.qiita.model.QiitaArticle
import dev.seabat.android.hellobottomnavi.domain.entity.QiitaArticleEntity
import dev.seabat.android.hellobottomnavi.domain.entity.QiitaArticleListEntity
import dev.seabat.android.hellobottomnavi.domain.exception.HelloException
import dev.seabat.android.hellobottomnavi.domain.repository.QiitaArticlesRepositoryContract
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Locale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QiitaArticlesRepository(private val endpoint: QiitaApiService) :
    QiitaArticlesRepositoryContract {
    override suspend fun fetchItems(query: String?): QiitaArticleListEntity? {
        // NOTE: 同期方式の場合はメインスレッド以外で通信する必要あり
        return withContext(Dispatchers.IO) {
            val response = try {
                // 同期方式で HTTP 通信を行う
                endpoint.getItems(
                    token = "Bearer ${BuildConfig.QIITA_TOKEN}",
                    page = "1",
                    per_page = "100",
                    query = query ?: "created:>2023-04-01"
                ).execute()
            } catch (e: Exception) { // 通信自体が失敗した場合
                val exception = HelloException.convertTo(e as Throwable)
                throw exception
            }

            if (response.isSuccessful) {
                val responseBody = response.body()
                val totalCount = response.headers().get("Total-Count")
                val entityList = convertToEntity(responseBody, totalCount?.toInt())
                entityList
            } else {
                val exception = QiitaExceptionConverter.convertTo(
                    response.code(),
                    response.errorBody()?.string()
                )
                throw exception
            }
        }
    }

    private fun convertToEntity(
        articles: Array<QiitaArticle>?,
        totalCount: Int?
    ): QiitaArticleListEntity? = articles?.let { nonNullArticles ->
        QiitaArticleListEntity(
            nonNullArticles.map {
                QiitaArticleEntity(
                    totalCount = totalCount,
                    createdAt = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.JAPAN).parse(
                        it.createdAt
                    )!!,
                    // NOTE: LocalDateTime#parse は Android O 以降で使用可能
                    title = it.title,
                    url = it.url,
                )
            } as ArrayList<QiitaArticleEntity>
        )
    }
}