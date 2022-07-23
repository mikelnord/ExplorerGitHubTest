package com.gb.android.explorergithubtest.presenter

import com.gb.android.explorergithubtest.model.User
import com.gb.android.explorergithubtest.repository.GitHubRepository
import com.gb.android.explorergithubtest.view.ViewContract
import retrofit2.Response


internal class UsersPresenter internal constructor(
    private val viewContract: ViewContract,
    private val repository: GitHubRepository
) : PresenterContract, GitHubRepository.GitHubRepositoryCallback {

    override fun listUsers() {
        viewContract.displayLoading(true)
        repository.listUsers(this)
    }

    override fun handleGitHubResponse(response: Response<List<User>>?) {
        viewContract.displayLoading(false)
        if (response != null && response.isSuccessful) {
            val usersResponse = response.body()
            if (usersResponse != null) {
                viewContract.displayListUsers(
                    usersResponse
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
