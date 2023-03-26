package com.example.githubrepositories.data.repository

import androidx.paging.*
import com.example.githubrepositories.Project
import com.example.githubrepositories.common.PAGE_SIZE
import com.example.githubrepositories.data.datasource.LocalProjectDataSource
import com.example.githubrepositories.data.datasource.RemoteProjectDataSource
import com.example.githubrepositories.data.repository.paging.ProjectPagingSource
import com.example.githubrepositories.data.repository.paging.ProjectRemoteMediator
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalPagingApi::class)

class ProjectRepositoryImpl(
    private val remoteMediator: ProjectRemoteMediator,
    private val projectPagingSource: ProjectPagingSource,
    private val remoteProjectDataSource: RemoteProjectDataSource,
    private val localProjectDataSource: LocalProjectDataSource
) : ProjectRepository {
    override suspend fun getProjects(page: Int, perPage: Int): Result<List<Project>> {
        return kotlin.runCatching {
            remoteProjectDataSource.fetchProjects(page, perPage)
        }.fold(onSuccess = { result ->
            localProjectDataSource.insertProjects(result.map { it.toProjectEntity() })
            Result.success(result.map { it.toProject() })
        }, onFailure = {
            Result.failure(it)
        })
    }

    override fun getProjectsStream(): Flow<PagingData<Project>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        remoteMediator = remoteMediator,
        pagingSourceFactory = { projectPagingSource },
    ).flow
}
