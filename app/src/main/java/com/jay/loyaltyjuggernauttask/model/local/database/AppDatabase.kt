package com.jay.loyaltyjuggernauttask.model.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jay.loyaltyjuggernauttask.model.local.entity.GitHubRepoEntity
import com.jay.loyaltyjuggernauttask.model.local.dao.GitHubRepoDao

@Database(
    entities = [GitHubRepoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun githubRepoDao(): GitHubRepoDao
}