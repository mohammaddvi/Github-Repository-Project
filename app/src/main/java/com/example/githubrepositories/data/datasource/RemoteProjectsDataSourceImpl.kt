package com.example.githubrepositories.data.datasource

import com.example.githubrepositories.data.ProjectDto
import com.example.githubrepositories.data.network.ApiClient
import kotlinx.coroutines.flow.Flow


interface RemoteProjectDataSource {
    suspend fun fetchProjects(page: Int, countPerPage: Int): List<ProjectDto>
}

class RemoteProjectsDataSourceImpl(private val apiClient: ApiClient) : RemoteProjectDataSource {
    override suspend fun fetchProjects(page: Int, countPerPage: Int) =
        apiClient.getAbnRepositories(page, countPerPage)
}