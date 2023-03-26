package com.example.githubrepositories

data class Project(
    val id: Long,
    val name: String,
    val fullName: String,
    val isPrivate: Boolean,
    val ownerAvatarUrl: String,
    val htmlUrl: String,
    val visibility: String,
    val description: String?,
)