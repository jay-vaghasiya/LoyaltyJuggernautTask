package com.jay.loyaltyjuggernauttask.model.repository

import com.jay.loyaltyjuggernauttask.model.local.entity.GitHubRepoEntity
import com.jay.loyaltyjuggernauttask.util.NetworkState

interface NetworkRepository {
    suspend fun searchRepositories(query: String): NetworkState<List<GitHubRepoEntity>>

}