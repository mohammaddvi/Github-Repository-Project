package com.example.githubrepositories.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.githubrepositories.common.INITIAL_PAGE
import com.example.githubrepositories.common.PAGE_SIZE
import com.example.githubrepositories.data.datasource.LocalProjectDataSource
import com.example.githubrepositories.data.datasource.RemoteProjectDataSource
import com.example.githubrepositories.Project
import com.example.githubrepositories.data.repository.toProject

class ProjectPagingSource(
    private val remoteProjectDataSource: RemoteProjectDataSource,
    private val localProjectDataSource: LocalProjectDataSource
) : PagingSource<Int, Project>() {
    override fun getRefreshKey(state: PagingState<Int, Project>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Project> {
        val nextPage = params.key ?: INITIAL_PAGE
        return try {
            val result = kotlin.runCatching {
                remoteProjectDataSource.fetchProjects(nextPage, PAGE_SIZE)
            }.fold(onSuccess = { result -> result.map { it.toProject() } },
                onFailure = {
                    localProjectDataSource.getAllProjects().map { it.toProject() }
                })

            val prevKey = if (nextPage == INITIAL_PAGE) null else nextPage - 1
            val nextKey = if (result.isEmpty()) null else nextPage + 1

            return LoadResult.Page(
                data = result,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}