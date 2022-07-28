package com.gb.android.explorergithubtest.repository

import com.gb.android.explorergithubtest.model.User
import retrofit2.Response

internal class FakeGitHubRepository : IGitHubRepository {
    private val user1 = User("mojombo", 1, "https://avatars.githubusercontent.com/u/1?v=4")
    private val user2 = User("defunkt", 2, "https://avatars.githubusercontent.com/u/2?v=4")
    private val user3 = User("pjhyett", 3, "https://avatars.githubusercontent.com/u/3?v=4")


    private val listUser = listOf(
        user1,
        user2,
        user3
    )

    override fun listUsers(callback: GitHubRepository.GitHubRepositoryCallback) {
        callback.handleGitHubResponse(Response.success(listUser),listUser.size)    }
}
