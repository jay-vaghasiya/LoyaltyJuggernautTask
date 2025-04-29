package com.jay.loyaltyjuggernauttask.model.repository

import com.jay.loyaltyjuggernauttask.model.local.dao.GitHubRepoDao
import com.jay.loyaltyjuggernauttask.model.local.entity.GitHubRepoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalGithubRepository(private val githubRepoDao: GitHubRepoDao) {


    suspend fun getSavedRepos(): List<GitHubRepoEntity> {
        return withContext(Dispatchers.IO) {
            githubRepoDao.getAllRepos()
        }
    }


    suspend fun saveRepos(repos: List<GitHubRepoEntity>) {
        withContext(Dispatchers.IO) {
            githubRepoDao.insertRepos(repos)
        }
    }


    suspend fun deleteRepos() {
        withContext(Dispatchers.IO) {
            githubRepoDao.clearRepos()
        }
    }
}

