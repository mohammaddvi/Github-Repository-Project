package com.example.githubrepositories.data.network.retrofit

import com.example.githubrepositories.data.network.ApiClient

class RetrofitApiImpl(private val retrofitApi: RetrofitApi) : ApiClient {
    override suspend fun getAbnRepositories(page: Int, countPerPage: Int) =
        retrofitApi.getAbnRepositories(page, countPerPage)
}