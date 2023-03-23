package com.example.githubrepositories.data.repository

data class Project(
    val id: Int,
    val name: String,
    val full_name: String,
    val private: Boolean,
    val avatar_url: String,
    val html_url: String,
    val visibility: VisibilityModel,
    val description: String,
)

enum class VisibilityModel {
    PUBLIC,
    PRIVATE
}