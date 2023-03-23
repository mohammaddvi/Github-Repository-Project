package com.example.githubrepositories.data.repository

import kotlinx.coroutines.flow.Flow


interface ProjectRepository {
     suspend fun fetchProjects(page: Int, countPerPage: Int):List<Project>
}