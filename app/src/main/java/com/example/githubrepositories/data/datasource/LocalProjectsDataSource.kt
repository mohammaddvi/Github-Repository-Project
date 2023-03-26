package com.example.githubrepositories.data.datasource

import androidx.paging.PagingSource
import com.example.githubrepositories.data.db.dao.ProjectDao
import com.example.githubrepositories.data.db.entities.ProjectEntity
import kotlinx.coroutines.flow.Flow

interface LocalProjectDataSource {
    suspend fun clearAllProjects()
    fun getAllProjects(): List<ProjectEntity>
    suspend fun insertProjects(projects: List<ProjectEntity>)
}

class LocalProjectsDataSourceImpl(private val projectDao: ProjectDao) : LocalProjectDataSource {

    override fun getAllProjects(): List<ProjectEntity> = projectDao.getAllProjects()

    override suspend fun clearAllProjects() = projectDao.clearAllProjects()

    override suspend fun insertProjects(projects: List<ProjectEntity>) =
        projectDao.insertProjects(projects)

}