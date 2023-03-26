package com.example.githubrepositories.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.githubrepositories.common.REMOTE_KEYS_TABLE_NAME

@Entity(tableName = REMOTE_KEYS_TABLE_NAME)
data class RemoteKeysEntity(
    @PrimaryKey var id: Long,
    val prevKey: Int?,
    val nextKey: Int?
)