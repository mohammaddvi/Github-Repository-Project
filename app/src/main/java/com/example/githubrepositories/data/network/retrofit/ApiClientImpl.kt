package com.example.githubrepositories.data.network.retrofit

import com.example.githubrepositories.data.network.ApiClient

class ApiClientImpl(private val retrofitApi: RetrofitApi) : ApiClient {
    override suspend fun getAbnRepositories(page: Int, countPerPage: Int) =
        retrofitApi.getAbnGithubRepositories(page, countPerPage)
}