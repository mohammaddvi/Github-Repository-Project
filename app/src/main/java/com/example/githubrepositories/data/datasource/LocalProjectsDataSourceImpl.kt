package com.example.githubrepositories.data.datasource

import com.example.githubrepositories.data.ProjectDto
import com.example.githubrepositories.data.db.ProjectDao
import kotlinx.coroutines.flow.Flow

interface LocalProjectDataSource {
    suspend fun updateProjects(projects: List<ProjectDto>)
    suspend fun fetchProjects(page: Int, countPerPage: Int): List<ProjectDto>
}

class LocalProjectsDataSourceImpl(private val projectDao: ProjectDao) : LocalProjectDataSource {
    override suspend fun fetchProjects(page: Int, countPerPage: Int) =
        projectDao.getAllRepositories(page, countPerPage)

    override suspend fun updateProjects(projects: List<ProjectDto>) =
        projectDao.insertProjects(projects)
}