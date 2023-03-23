package com.example.githubrepositories.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projects")
data class ProjectDto(
    @PrimaryKey val id: Int,

    @ColumnInfo(name = "name") val name: String,

    @ColumnInfo(name = "full_name") val fullName: String,

    @ColumnInfo(name = "description") val description: String,

    @ColumnInfo(name = "avatar_url") val avatarUrl: String,

    @ColumnInfo(name = "visibility") val visibility: String,

    @ColumnInfo(name = "html_url") val htmlUrl: String,

    @ColumnInfo(name = "private") val isPrivate: Boolean,

    )
