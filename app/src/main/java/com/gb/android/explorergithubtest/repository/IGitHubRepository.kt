package com.gb.android.explorergithubtest.repository

import com.gb.android.explorergithubtest.model.User

internal interface IGitHubRepository {
    fun listUsers(
        callback: GitHubRepository.GitHubRepositoryCallback
    )

    suspend fun listUsersAsync(): List<User>?
}