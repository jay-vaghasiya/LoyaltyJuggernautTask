package com.jay.loyaltyjuggernauttask.remote

object NetworkInstance {
    val api: NetworkService by lazy {
        NetworkModule.provideRetrofit()
            .create(NetworkService::class.java)
    }
}