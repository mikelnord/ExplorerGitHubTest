package com.gb.android.explorergithubtest.repository

import androidx.lifecycle.LiveData
import com.gb.android.explorergithubtest.model.Repo
import com.gb.android.explorergithubtest.model.User
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

internal interface IDataSource {

    @GET("/users")
    fun listUsers(): Call<List<User>>

    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String): Call<List<Repo>>

    @GET("/users")
    suspend fun listUsersAsync(): List<User>

}
