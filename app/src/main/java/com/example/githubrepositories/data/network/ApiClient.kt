package com.example.githubrepositories.data.network

interface ApiClient {
    suspend fun getAbnRepositories(page: Int, countPerPage: Int): List<ProjectResponseDto>
}