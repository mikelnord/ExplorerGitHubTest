package com.gb.android.explorergithubtest.view

import com.gb.android.explorergithubtest.model.User


interface ViewContract {
    fun displayListUsers(
        usersList: List<User>
    )

    fun displayError()
    fun displayError(error: String)
    fun displayLoading(show: Boolean)
}
