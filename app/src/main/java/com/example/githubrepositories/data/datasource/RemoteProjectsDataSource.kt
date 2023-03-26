package com.example.githubrepositories.data.datasource

import com.example.githubrepositories.data.network.ApiClient
import com.example.githubrepositories.data.network.ProjectResponseDto


interface RemoteProjectDataSource {
    suspend fun fetchProjects(page: Int, countPerPage: Int): List<ProjectResponseDto>
}

class RemoteProjectsDataSourceImpl(private val apiClient: ApiClient) : RemoteProjectDataSource {
    override suspend fun fetchProjects(page: Int, countPerPage: Int) =
        apiClient.getAbnRepositories(page, countPerPage)
}