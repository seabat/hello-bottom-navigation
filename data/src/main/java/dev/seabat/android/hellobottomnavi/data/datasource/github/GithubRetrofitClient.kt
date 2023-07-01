package dev.seabat.android.hellobottomnavi.data.datasource.github

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GithubRetrofitClient {
    const val baseUrl = "https://api.github.com/"

    private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun createApiEndpoint(): GithubApiEndpoint  {
        return retrofit.create(GithubApiEndpoint::class.java)
    }
}