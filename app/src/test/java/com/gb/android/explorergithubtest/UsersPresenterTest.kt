package com.gb.android.explorergithubtest

import com.gb.android.explorergithubtest.model.User
import com.gb.android.explorergithubtest.presenter.UsersPresenter
import com.gb.android.explorergithubtest.repository.GitHubRepository
import com.gb.android.explorergithubtest.view.ViewContract
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response


class UsersPresenterTest {

    private lateinit var presenter: UsersPresenter

    @Mock
    private lateinit var repository: GitHubRepository

    @Mock
    private lateinit var viewContract: ViewContract

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = UsersPresenter(viewContract, repository)
    }

    @Test
    fun listUsers_Test() {
        presenter.listUsers()
        Mockito.verify(repository, Mockito.times(1)).listUsers(presenter)
    }

    @Test
    fun handleGitHubError_Test() {
        presenter.handleGitHubError()
        Mockito.verify(viewContract, Mockito.times(1)).displayError()
    }

    @Test
    fun handleGitHubResponse_ResponseUnsuccessful() {
        val response = Mockito.mock(Response::class.java) as Response<User?>
        Mockito.`when`(response.isSuccessful).thenReturn(false)
        Assert.assertFalse(response.isSuccessful)
    }

    @Test
    fun handleGitHubResponse_Failure() {
        val response = Mockito.mock(Response::class.java) as Response<List<User>>
        Mockito.`when`(response.isSuccessful).thenReturn(false)
        presenter.handleGitHubResponse(response)
        Mockito.verify(viewContract, Mockito.times(1))
            .displayError("Response is null or unsuccessful")
    }

    @Test
    fun handleGitHubResponse_ResponseFailure_ViewContractMethodOrder() {
        val response = Mockito.mock(Response::class.java) as Response<List<User>>
        Mockito.`when`(response.isSuccessful).thenReturn(false)
        presenter.handleGitHubResponse(response)
        val inOrder = Mockito.inOrder(viewContract)
        inOrder.verify(viewContract).displayLoading(false)
        inOrder.verify(viewContract).displayError("Response is null or unsuccessful")
    }

    @Test
    fun handleGitHubResponse_ResponseIsEmpty() {
        val response = Mockito.mock(Response::class.java) as Response<List<User>>
        Mockito.`when`(response.body()).thenReturn(null)
        presenter.handleGitHubResponse(response)
        Assert.assertNull(response.body())
    }

    @Test
    fun handleGitHubResponse_ResponseIsNotEmpty() {
        val response = Mockito.mock(Response::class.java) as Response<List<User>>
        Mockito.`when`(response.body()).thenReturn(listOf(Mockito.mock(User::class.java)))
        presenter.handleGitHubResponse(response)
        Assert.assertNotNull(response.body())
    }

    @Test
    fun handleGitHubResponse_EmptyResponse() {
        val response = Mockito.mock(Response::class.java) as Response<List<User>>
        Mockito.`when`(response.isSuccessful).thenReturn(true)
        Mockito.`when`(response.body()).thenReturn(null)
        presenter.handleGitHubResponse(response)
        Mockito.verify(viewContract, Mockito.times(1))
            .displayError("List users null")
    }

    @Test
    fun handleGitHubResponse_Success() {
        val response = Mockito.mock(Response::class.java) as Response<List<User>>
        val listUser = listOf(Mockito.mock(User::class.java))
        Mockito.`when`(response.isSuccessful).thenReturn(true)
        Mockito.`when`(response.body()).thenReturn(listUser)
        presenter.handleGitHubResponse(response)
        Mockito.verify(viewContract, Mockito.times(1)).displayListUsers(listUser)
    }

}