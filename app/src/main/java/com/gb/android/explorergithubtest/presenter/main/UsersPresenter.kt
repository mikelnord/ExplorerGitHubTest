package com.gb.android.explorergithubtest.presenter.main

import com.gb.android.explorergithubtest.model.User
import com.gb.android.explorergithubtest.repository.GitHubRepository
import com.gb.android.explorergithubtest.repository.IGitHubRepository
import com.gb.android.explorergithubtest.view.main.ViewContractMain
import retrofit2.Response


internal class UsersPresenter internal constructor(
    private val viewContract: ViewContractMain,
    private val repository: IGitHubRepository
) : PresenterMainContract, GitHubRepository.GitHubRepositoryCallback {

    override fun listUsers() {
        viewContract.displayLoading(true)
        repository.listUsers(this)
    }

    override fun handleGitHubResponse(response: Response<List<User>>?,count:Int) {
        viewContract.displayLoading(false)
        if (response != null && response.isSuccessful) {
            val usersResponse = response.body()
            if (usersResponse != null) {
                viewContract.displayListUsers(
                    usersResponse, count
                )
            } else {
                viewContract.displayError("List users null")
            }
        } else {
            viewContract.displayError("Response is null or unsuccessful")
        }
    }

    override fun handleGitHubError() {
        viewContract.displayLoading(false)
        viewContract.displayError()
    }

}
