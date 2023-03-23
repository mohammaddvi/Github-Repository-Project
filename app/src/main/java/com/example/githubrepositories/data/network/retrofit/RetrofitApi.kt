package com.example.githubrepositories.data.network.retrofit

import com.example.githubrepositories.data.ProjectDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {
    @GET("users/abnamrocoesd/repos")
    suspend fun getAbnRepositories(
        @Query("page") page: Int,
        @Query("per_page") countPerPage: Int
    ): List<ProjectDto>
}