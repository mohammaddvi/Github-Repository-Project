package com.example.githubrepositories.data.repository

import androidx.paging.PagingData
import com.example.githubrepositories.Project
import kotlinx.coroutines.flow.Flow
interface ProjectRepository {

     suspend fun getProjects(page:Int,perPage:Int): Result<List<Project>>
     fun getProjectsStream(): Flow<PagingData<Project>>

}