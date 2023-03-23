package com.example.githubrepositories.data.network

import com.example.githubrepositories.data.ProjectDto
import kotlinx.coroutines.flow.Flow

interface ApiClient {
    suspend fun getAbnRepositories(page: Int, countPerPage: Int): List<ProjectDto>
}