package com.example.githubrepositories

import com.example.githubrepositories.data.ProjectDto
import com.example.githubrepositories.data.datasource.LocalProjectDataSource
import com.example.githubrepositories.data.datasource.RemoteProjectDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLocalDataSource : LocalProjectDataSource {
    override suspend fun updateProjects(projects: List<ProjectDto>) {
    }

    override fun fetchProjects(page: Int, countPerPage: Int): Flow<List<ProjectDto>> = flow {
        fakeLocalData
    }
}

class FakeRemoteDataSource : RemoteProjectDataSource {
    override suspend fun fetchProjects(page: Int, countPerPage: Int): List<ProjectDto> =
        fakeRemoteData
}