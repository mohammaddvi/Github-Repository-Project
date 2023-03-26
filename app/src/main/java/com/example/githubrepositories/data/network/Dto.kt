package com.example.githubrepositories.data.network

import com.google.gson.annotations.SerializedName


data class ProjectResponseDto(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("private") val isPrivate: Boolean,
    @SerializedName("description") val description: String,
    @SerializedName("visibility") val visibility: String,
    @SerializedName("html_url") val htmlUrl: String,
    @SerializedName("owner") val owner: ProjectOwnerResponseDto
)

data class ProjectOwnerResponseDto(@SerializedName("avatar_url") val avatarUrl: String)
