package com.jay.loyaltyjuggernauttask.model.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "github_repos")
data class GitHubRepoEntity(
    @PrimaryKey val id: Int?,
    val repoName: String,
    val username: String?,
    val htmlUrl: String
)