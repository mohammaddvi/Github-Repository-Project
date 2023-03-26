package com.example.githubrepositories.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.githubrepositories.data.db.dao.ProjectDao
import com.example.githubrepositories.data.db.dao.RemoteKeysDao
import com.example.githubrepositories.data.db.entities.ProjectEntity
import com.example.githubrepositories.data.db.entities.RemoteKeysEntity

@Database(
    entities = [ProjectEntity::class,RemoteKeysEntity::class],
    version = 3
)
abstract class ProjectsDatabase : RoomDatabase() {
    abstract fun projectDao(): ProjectDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}