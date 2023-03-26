package com.example.githubrepositories.data.repository.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.githubrepositories.common.INITIAL_PAGE
import com.example.githubrepositories.data.datasource.LocalProjectDataSource
import com.example.githubrepositories.data.datasource.RemoteProjectDataSource
import com.example.githubrepositories.data.db.dao.RemoteKeysDao
import com.example.githubrepositories.data.db.entities.RemoteKeysEntity
import com.example.githubrepositories.Project
import com.example.githubrepositories.data.repository.toProjectEntity

@OptIn(ExperimentalPagingApi::class)
class ProjectRemoteMediator(
    private val remoteProjectDataSource: RemoteProjectDataSource,
    private val localDataSource: LocalProjectDataSource,
    private val remoteKeysDao: RemoteKeysDao
) : RemoteMediator<Int, Project>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.SKIP_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Project>
    ): MediatorResult {
        return try {
            val page: Int = when (loadType) {
                LoadType.REFRESH -> INITIAL_PAGE
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    remoteKeys?.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
            }
            kotlin.runCatching {
                remoteProjectDataSource.fetchProjects(page, state.config.pageSize)
            }.fold(onSuccess = { projects ->
                val endOfPaginationReached = projects.isEmpty()

                if (loadType == LoadType.REFRESH) {
                    remoteKeysDao.clearRemoteKeys()
                    localDataSource.clearAllProjects()
                }

                val previousKey = if (page == INITIAL_PAGE) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                val remoteKeys = projects.map {
                    RemoteKeysEntity(
                        id = it.id,
                        prevKey = previousKey,
                        nextKey = nextKey,
                    )
                }
                localDataSource.insertProjects(projects.map { it.toProjectEntity() })
                remoteKeysDao.insertAll(remoteKeys)
                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            }, onFailure = {
                MediatorResult.Error(it)
            })
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Project>): RemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo -> remoteKeysDao.getRemoteKey(repo.id) }
    }

}