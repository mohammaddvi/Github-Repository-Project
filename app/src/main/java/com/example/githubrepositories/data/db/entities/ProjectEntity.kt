package com.example.githubrepositories.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.githubrepositories.common.PROJECT_TABLE_NAME

@Entity(tableName = PROJECT_TABLE_NAME)
data class ProjectEntity(
    @PrimaryKey @ColumnInfo("id") val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "full_name") val fullName: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "owner_avatar_url") val ownerAvatarUrl: String,
    @ColumnInfo(name = "visibility") val visibility: String,
    @ColumnInfo(name = "html_url") val htmlUrl: String,
    @ColumnInfo(name = "private") val isPrivate: Boolean,
)
