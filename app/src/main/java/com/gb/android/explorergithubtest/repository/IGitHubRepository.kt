package com.gb.android.explorergithubtest.repository

internal interface IGitHubRepository {
    fun listUsers(
        callback: GitHubRepository.GitHubRepositoryCallback
    )
}