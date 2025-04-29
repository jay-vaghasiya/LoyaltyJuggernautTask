package com.jay.loyaltyjuggernauttask.remote

import com.jay.loyaltyjuggernauttask.model.datamodel.GithubDataModel
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String = "stars",
        @Query("order") order: String = "desc"
    ): Response<GithubDataModel>
}