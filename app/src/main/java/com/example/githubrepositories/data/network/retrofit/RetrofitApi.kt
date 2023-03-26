package com.example.githubrepositories.data.network.retrofit

import com.example.githubrepositories.data.network.ProjectResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {
    @GET("users/abnamrocoesd/repos")
    suspend fun getAbnGithubRepositories(
        @Query("page") page: Int,
        @Query("per_page") countPerPage: Int
    ): List<ProjectResponseDto>
}