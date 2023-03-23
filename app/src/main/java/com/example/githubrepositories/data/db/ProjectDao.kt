package com.example.githubrepositories.data.db

import androidx.room.*
import com.example.githubrepositories.data.ProjectDto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProjects(projects: List<ProjectDto>)

    @Query("SELECT * FROM projects LIMIT :countPerPage OFFSET :offset")
    suspend fun getAllRepositories(page: Int, countPerPage: Int): List<ProjectDto> {
        val offset = (page - 1) * countPerPage
        return getAllRepositories(countPerPage, offset)
    }

}