package com.gb.android.explorergithubtest.view.main

import com.gb.android.explorergithubtest.model.User
import com.gb.android.explorergithubtest.view.ViewContract


interface ViewContractMain:ViewContract {
    fun displayListUsers(
        usersList: List<User>
    )

    fun displayError()
    fun displayError(error: String)
    fun displayLoading(show: Boolean)
}
