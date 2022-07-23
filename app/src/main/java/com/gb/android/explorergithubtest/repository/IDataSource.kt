package com.gb.android.explorergithubtest.repository

import com.gb.android.explorergithubtest.model.Repo
import com.gb.android.explorergithubtest.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

internal interface IDataSource {

    @GET("/users")
    fun listUsers(): Call<List<User>>

    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String): Call<List<Repo>>
}
