package com.example.githubrepositories

import androidx.paging.PagingData
import com.example.githubrepositories.data.network.ProjectResponseDto
import com.example.githubrepositories.data.repository.toProject

internal fun List<ProjectResponseDto>.toPagingData() = PagingData.from(this.map { it.toProject() })

internal fun List<ProjectResponseDto>.toProject() = this.map { it.toProject() }

