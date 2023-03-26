package com.example.githubrepositories.data.db.dao

import androidx.room.*
import com.example.githubrepositories.common.PROJECT_TABLE_NAME
import com.example.githubrepositories.data.db.entities.ProjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProjects(projects: List<ProjectEntity>)

    @Query("SELECT * FROM $PROJECT_TABLE_NAME")
    fun getProjectStream(): Flow<List<ProjectEntity>>

    @Query("SELECT * FROM $PROJECT_TABLE_NAME")
    fun getAllProjects(): List<ProjectEntity>


    @Query("DELETE FROM $PROJECT_TABLE_NAME")
    suspend fun clearAllProjects()

}