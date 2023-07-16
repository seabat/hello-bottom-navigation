package dev.seabat.android.hellobottomnavi.data.repository

import android.text.format.DateUtils
import dev.seabat.android.hellobottomnavi.data.BuildConfig
import dev.seabat.android.hellobottomnavi.data.datasource.qiita.QiitaApiService
import dev.seabat.android.hellobottomnavi.data.datasource.qiita.QiitaExceptionConverter
import dev.seabat.android.hellobottomnavi.data.datasource.qiita.model.QiitaArticle
import dev.seabat.android.hellobottomnavi.domain.entity.QiitaArticleEntity
import dev.seabat.android.hellobottomnavi.domain.entity.QiitaArticleListEntity
import dev.seabat.android.hellobottomnavi.domain.exception.HelloException
import dev.seabat.android.hellobottomnavi.domain.repository.QiitaArticlesRepositoryContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Locale

class QiitaArticlesRepository(
    private val endpoint: QiitaApiService
) : QiitaArticlesRepositoryContract {
    override suspend fun fetchItems(query: String?): QiitaArticleListEntity? {
        //NOTE: 同期方式の場合はメインスレッド以外で通信する必要あり
        return withContext(Dispatchers.IO) {
            val response = try {
                // 同期方式で HTTP 通信を行う
                endpoint.getItems(
                    token = "Bearer ${BuildConfig.QIITA_TOKEN}",
                    page= "1",
                    per_page = "100",
                    query= query ?: "created:>2023-04-01"
                ).execute()
            } catch (e: Exception) { // 通信自体が失敗した場合
                val exception = HelloException.convertTo(e as Throwable)
                throw exception
            }

            if (response.isSuccessful) {
                val responseBody = response.body()
                val entityList = convertToEntity(responseBody)
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

    private fun convertToEntity(articles: Array<QiitaArticle>?): QiitaArticleListEntity? {
        return articles?.let { nonNullArticles ->
            QiitaArticleListEntity(
                nonNullArticles.map {
                    QiitaArticleEntity(
                        createdAt = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.JAPAN).parse(it.createdAt),
                        // NOTE: LocalDateTime#parse は Android O 以降で使用可能
                        title = it.title,
                        url = it.url,
                    )
                } as ArrayList<QiitaArticleEntity>
            )
        } ?: null
    }
}