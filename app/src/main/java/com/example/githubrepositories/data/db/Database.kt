package com.example.githubrepositories.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.githubrepositories.data.repository.Project

@Database(
    entities = [Project::class],
    version = 1
)
abstract class ProjectsDatabase : RoomDatabase() {
    abstract fun projectDao(): ProjectDao
}