package com.gb.android.explorergithubtest.repository

import com.gb.android.explorergithubtest.model.Repo
import com.gb.android.explorergithubtest.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IDataSource {

//    @Headers("Accept: application/vnd.github.mercy-preview+json")
//    @GET("search/repositories")
//    fun searchGithub(@Query("q") term: String?): Call<SearchResponse?>?

    @GET("/users")
    fun listUsers(): Call<List<User>>

    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String): Call<List<Repo>>
}
