package com.example.githubrepositories.data.datasource

import com.example.githubrepositories.data.ProjectDto
import com.example.githubrepositories.data.db.ProjectDao
import kotlinx.coroutines.flow.Flow

interface LocalProjectDataSource {
    suspend fun updateProjects(projects: List<ProjectDto>)
    suspend fun fetchProjects(perPage: Int, offset: Int): List<ProjectDto>
}

class LocalProjectsDataSourceImpl(private val projectDao: ProjectDao) : LocalProjectDataSource {
    override suspend fun fetchProjects(perPage: Int, offset: Int) =
        projectDao.getAllRepositories(perPage, offset)

    override suspend fun updateProjects(projects: List<ProjectDto>) =
        projectDao.insertProjects(projects)
}