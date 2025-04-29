package com.jay.loyaltyjuggernauttask.model.datamodel


data class GithubDataModel(
    val incomplete_results: Boolean,
    val items: List<Item?>,
    val total_count: Int
)