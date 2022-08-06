package com.gb.android.explorergithubtest.repository

import androidx.lifecycle.LiveData
import com.gb.android.explorergithubtest.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await

internal class GitHubRepository(private val iDataSource: IDataSource) : IGitHubRepository {

    override fun listUsers(
        callback: GitHubRepositoryCallback
    ) {
        val call = iDataSource.listUsers()
        call.enqueue(object : Callback<List<User>> {

            override fun onResponse(
                call: Call<List<User>>,
                response: Response<List<User>>
            ) {
                callback.handleGitHubResponse(response, response.body()?.size ?: 0)
            }

            override fun onFailure(
                call: Call<List<User>>,
                t: Throwable
            ) {
                callback.handleGitHubError()
            }
        })
    }

    override suspend fun listUsersAsync(): List<User> {
        return iDataSource.listUsersAsync()
    }

    interface GitHubRepositoryCallback {
        fun handleGitHubResponse(response: Response<List<User>>?, count: Int)
        fun handleGitHubError()
    }
}
