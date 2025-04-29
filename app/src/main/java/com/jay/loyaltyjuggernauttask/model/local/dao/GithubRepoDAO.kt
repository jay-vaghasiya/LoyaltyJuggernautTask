package com.jay.loyaltyjuggernauttask.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jay.loyaltyjuggernauttask.model.local.entity.GitHubRepoEntity

@Dao
interface GitHubRepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepos(repos: List<GitHubRepoEntity>)

    @Query("SELECT * FROM github_repos ORDER BY id ASC")
    suspend fun getAllRepos(): List<GitHubRepoEntity>

    @Query("DELETE FROM github_repos")
    suspend fun clearRepos()
}