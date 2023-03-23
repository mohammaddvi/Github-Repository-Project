package com.example.githubrepositories.data.repository

import android.util.Log
import com.example.githubrepositories.data.datasource.LocalProjectDataSource
import com.example.githubrepositories.data.datasource.RemoteProjectDataSource
import org.koin.core.component.getScopeId

class ProjectRepositoryImpl(
    private val localProjectDataSource: LocalProjectDataSource,
    private val remoteProjectDataSource: RemoteProjectDataSource,
) : ProjectRepository {

    override suspend fun fetchProjects(page: Int, countPerPage: Int): List<Project> {
        kotlin.runCatching {
            remoteProjectDataSource.fetchProjects(page, countPerPage)
        }.onSuccess {
            localProjectDataSource.updateProjects(it)
        }.onFailure {}

        val local = localProjectDataSource.fetchProjects(countPerPage, page * countPerPage)
        return if (local.isEmpty()) emptyList()
        else local.map { it.toProject() }
    }
}

