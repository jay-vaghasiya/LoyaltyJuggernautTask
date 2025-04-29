package com.jay.loyaltyjuggernauttask.model.repository

import com.jay.loyaltyjuggernauttask.model.datamodel.GithubDataModel
import com.jay.loyaltyjuggernauttask.model.local.dao.GitHubRepoDao
import com.jay.loyaltyjuggernauttask.model.local.entity.GitHubRepoEntity
import com.jay.loyaltyjuggernauttask.remote.NetworkInstance
import com.jay.loyaltyjuggernauttask.util.NetworkState


class NetworkRepositoryImpl(
    private val localGithubRepository: LocalGithubRepository
) : NetworkRepository {

    override suspend fun searchRepositories(query: String): NetworkState<List<GitHubRepoEntity>> {
        return try {
            if (query.isBlank()) {

                val localRepos = localGithubRepository.getSavedRepos()
                return if (localRepos.isNotEmpty()) {
                    NetworkState.Success(localRepos)
                } else {
                    NetworkState.Error("No cached data available")
                }
            }


            val response = NetworkInstance.api.searchRepositories(
                query = query,
                sort = "stars",
                order = "desc"
            )

            if (response.isSuccessful) {
                response.body()?.let { githubDataModel ->

                    val repoEntities = githubDataModel.items.map { item ->
                        GitHubRepoEntity(
                            id = item?.id ?: 0,
                            repoName = item?.name.orEmpty(),
                            username = item?.owner?.login,
                            htmlUrl = item?.html_url.orEmpty()
                        )
                    }


                    localGithubRepository.saveRepos(repoEntities)

                    NetworkState.Success(repoEntities)
                } ?: NetworkState.Error("Empty response body")
            } else {
                NetworkState.Error("API Error: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            NetworkState.Error(e.localizedMessage ?: "Unknown error")
        }
    }
}


