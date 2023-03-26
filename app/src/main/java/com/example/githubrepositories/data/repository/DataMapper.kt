package com.example.githubrepositories.data.repository

import com.example.githubrepositories.Project
import com.example.githubrepositories.data.db.entities.ProjectEntity
import com.example.githubrepositories.data.network.ProjectResponseDto

fun ProjectEntity.toProject() = Project(
    this.id,
    this.name,
    this.fullName,
    this.isPrivate,
    this.ownerAvatarUrl,
    this.htmlUrl,
    this.visibility,
    this.description
)

fun ProjectResponseDto.toProjectEntity() = ProjectEntity(
    id = id,
    name = name,
    fullName = fullName,
    description = description,
    ownerAvatarUrl = owner.avatarUrl,
    visibility = visibility,
    htmlUrl = htmlUrl,
    isPrivate = isPrivate
)

fun ProjectResponseDto.toProject() = Project(
    id = id,
    name = name,
    fullName = fullName,
    description = description,
    ownerAvatarUrl = owner.avatarUrl,
    visibility = visibility,
    htmlUrl = htmlUrl,
    isPrivate = isPrivate
)
