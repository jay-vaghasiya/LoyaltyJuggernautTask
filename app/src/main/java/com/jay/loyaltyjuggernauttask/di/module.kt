package com.jay.loyaltyjuggernauttask.di

import androidx.room.Room
import com.jay.loyaltyjuggernauttask.model.local.database.AppDatabase
import com.jay.loyaltyjuggernauttask.model.local.dao.GitHubRepoDao
import com.jay.loyaltyjuggernauttask.model.repository.LocalGithubRepository
import com.jay.loyaltyjuggernauttask.model.repository.NetworkRepository
import com.jay.loyaltyjuggernauttask.model.repository.NetworkRepositoryImpl
import com.jay.loyaltyjuggernauttask.viewmodel.NetworkViewModel
import com.jay.loyaltyjuggernauttask.viewmodel.StateViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val module = module {
    viewModel { StateViewModel() }
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "github_repo_db"
        ).build()
    }

    single<GitHubRepoDao> {
        get<AppDatabase>().githubRepoDao()
    }

    single<NetworkRepository> {
        NetworkRepositoryImpl(get())
    }
    single { LocalGithubRepository(get()) }
    viewModel { NetworkViewModel(get(),get()) }

}