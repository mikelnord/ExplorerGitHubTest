package com.gb.android.explorergithubtest.repository

import com.gb.android.explorergithubtest.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class GitHubRepository(private val iDataSource: IDataSource) {

    fun listUsers(
        callback: GitHubRepositoryCallback
    ) {
        val call = iDataSource.listUsers()
        call.enqueue(object : Callback<List<User>> {

            override fun onResponse(
                call: Call<List<User>>,
                response: Response<List<User>>
            ) {
                callback.handleGitHubResponse(response)
            }

            override fun onFailure(
                call: Call<List<User>>,
                t: Throwable
            ) {
                callback.handleGitHubError()
            }
        })
    }

    interface GitHubRepositoryCallback {
        fun handleGitHubResponse(response: Response<List<User>>?)
        fun handleGitHubError()
    }
}
