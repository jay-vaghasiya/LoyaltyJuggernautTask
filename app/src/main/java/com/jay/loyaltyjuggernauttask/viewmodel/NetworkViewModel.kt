package com.jay.loyaltyjuggernauttask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jay.loyaltyjuggernauttask.model.local.entity.GitHubRepoEntity
import com.jay.loyaltyjuggernauttask.model.repository.LocalGithubRepository
import com.jay.loyaltyjuggernauttask.model.repository.NetworkRepository
import com.jay.loyaltyjuggernauttask.util.NetworkState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class NetworkViewModel(
    private val networkRepository: NetworkRepository,
    private val localGithubRepository: LocalGithubRepository
) : ViewModel() {

    private val _repositories = MutableStateFlow<NetworkState<List<GitHubRepoEntity>>>(NetworkState.Loading)
    val repositories: StateFlow<NetworkState<List<GitHubRepoEntity>>> = _repositories


    fun fetchRepositories(query: String) {
        viewModelScope.launch {
            if (query.isBlank()) {

                _repositories.value = NetworkState.Loading
                _repositories.value = NetworkState.Success(localGithubRepository.getSavedRepos())
            } else {

                _repositories.value = NetworkState.Loading
                val networkState = networkRepository.searchRepositories(query)
                if (networkState is NetworkState.Success) {

                    localGithubRepository.saveRepos(networkState.data)
                }
                _repositories.value = networkState
            }
        }
    }
}

