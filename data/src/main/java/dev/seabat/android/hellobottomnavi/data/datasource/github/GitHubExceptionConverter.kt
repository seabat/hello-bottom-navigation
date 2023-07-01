package dev.seabat.android.hellobottomnavi.data.datasource.github

import com.google.gson.Gson
import dev.seabat.android.hellobottomnavi.data.datasource.github.model.ErrorResponse
import dev.seabat.android.hellobottomnavi.domain.exception.ErrorType
import dev.seabat.android.hellobottomnavi.domain.exception.HelloException

/**
 * GitHub API のエラーレスポンスコードを HelloException に変換する
 */
object GitHubExceptionConverter {
    fun convertTo(responseStatusCode: Int, errorBody: String?): HelloException {
        val errorType = when (responseStatusCode) {
            400 -> ErrorType.NETWORK_BAD_REQUEST
            401 -> ErrorType.NETWORK_UNAUTHORIZED
            403 -> ErrorType.NETWORK_FORBIDDEN
            404 -> ErrorType.NETWORK_NOT_FOUND
            else -> ErrorType.NETWORK_UNKNOWN_ERROR
        }
        val errorMessage = errorBody?.let {
            Gson().fromJson<ErrorResponse>(it, ErrorResponse::class.java)
        } ?: null

        return HelloException.HttpException(
            errType = errorType,
            responseStatus = responseStatusCode,
            errMessage = errorMessage?.message ?: null
        )
    }
}