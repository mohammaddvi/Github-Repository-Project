package com.example.githubrepositories

import com.example.githubrepositories.data.ProjectDto
import com.example.githubrepositories.data.datasource.LocalProjectDataSource
import com.example.githubrepositories.data.datasource.RemoteProjectDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLocalDataSource : LocalProjectDataSource {
    var fakeData : List<ProjectDto> = fakeLocalData
    override suspend fun updateProjects(projects: List<ProjectDto>) {
        fakeData = projects
    }

    override suspend fun fetchProjects(page: Int, countPerPage: Int): List<ProjectDto> =
        fakeData

}

class FakeRemoteDataSource : RemoteProjectDataSource {
    override suspend fun fetchProjects(page: Int, countPerPage: Int): List<ProjectDto> =
        fakeRemoteData
}