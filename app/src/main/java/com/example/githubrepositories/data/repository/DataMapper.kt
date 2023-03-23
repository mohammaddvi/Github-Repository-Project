package com.example.githubrepositories.data.repository

import com.example.githubrepositories.data.ProjectDto

fun ProjectDto.toProject() = Project(
    this.id,
    this.name,
    this.fullName,
    this.isPrivate,
    this.avatarUrl,
    this.htmlUrl,
    this.visibility.toVisibilityModel(),
    this.description
)

private fun String.toVisibilityModel() =
    if (this == VisibilityModel.PUBLIC.name) VisibilityModel.PUBLIC else VisibilityModel.PRIVATE