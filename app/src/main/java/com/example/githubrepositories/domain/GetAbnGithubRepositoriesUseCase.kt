package com.example.githubrepositories.domain

import com.example.githubrepositories.data.repository.ProjectRepository

class GetAbnGithubRepositoriesUseCase(private val projectRepository: ProjectRepository) {
    suspend operator fun invoke(page: Int, countPerPage: Int) {
        projectRepository.fetchProjects(page, countPerPage)
    }
}