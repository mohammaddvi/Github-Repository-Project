package com.example.githubrepositories

import com.example.githubrepositories.data.db.entities.ProjectEntity
import com.example.githubrepositories.data.network.ProjectOwnerResponseDto
import com.example.githubrepositories.data.network.ProjectResponseDto

val fakeRemoteData = listOf(
    ProjectResponseDto(
        1,
        "test project",
        "mohammad",
        false,
        "www.myavatar.com",
        "Public",
        "www.github.com",
        ProjectOwnerResponseDto("www.myavatar.com")
    ),
    ProjectResponseDto(
        2,
        "test project 2",
        "mohammad",
        true,
        "www.myavatar.com",
        "Private",
        "www.github.com",
        ProjectOwnerResponseDto("www.myavatar.com")

    ),
    ProjectResponseDto(
        3,
        "test project 3",
        "mohammad",
        false,
        "www.myavatar.com",
        "Public",
        "www.github.com",
        ProjectOwnerResponseDto("www.myavatar.com")

    ),
)

val fakeLocalData = listOf(
    ProjectEntity(
        1,
        "test project local",
        "mohammad",
        "This is a test project",
        "www.myavatar.com",
        "Public",
        "www.github.com",
        false
    ),
    ProjectEntity(
        2,
        "test project local 2",
        "mohammad",
        "This is a test project",
        "www.myavatar.com",
        "Private",
        "www.github.com",
        true
    ),
    ProjectEntity(
        3,
        "test project local 3",
        "mohammad",
        "This is a test project",
        "www.myavatar.com",
        "Public",
        "www.github.com",
        false
    ),
)