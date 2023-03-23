package com.example.githubrepositories.data.repository

import android.util.Log
import com.example.githubrepositories.data.datasource.LocalProjectDataSource
import com.example.githubrepositories.data.datasource.RemoteProjectDataSource

class ProjectRepositoryImpl(
    private val localProjectDataSource: LocalProjectDataSource,
    private val remoteProjectDataSource: RemoteProjectDataSource,
) : ProjectRepository {

    override suspend fun fetchProjects(page: Int, countPerPage: Int): List<Project> {
        kotlin.runCatching {
            remoteProjectDataSource.fetchProjects(page, countPerPage)
        }.onSuccess {
            localProjectDataSource.updateProjects(it)
        }.onFailure {
            Log.d("mogger", "failed")
        }

        return localProjectDataSource.fetchProjects(page, countPerPage).map { it.toProject() }
    }
}

