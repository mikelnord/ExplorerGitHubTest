package com.gb.android.explorergithubtest

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gb.android.explorergithubtest.model.User
import com.gb.android.explorergithubtest.repository.FakeGitHubRepository
import com.gb.android.explorergithubtest.view.main.MainViewModel
import com.gb.android.explorergithubtest.view.main.ScreenState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    private lateinit var mainViewModel: MainViewModel

    @Mock
    private lateinit var repository: FakeGitHubRepository

    private val user1 = User("mojombo", 1, "https://avatars.githubusercontent.com/u/1?v=4")
    private val user2 = User("defunkt", 2, "https://avatars.githubusercontent.com/u/2?v=4")
    private val user3 = User("pjhyett", 3, "https://avatars.githubusercontent.com/u/3?v=4")


    private val listUser = listOf(
        user1,
        user2,
        user3
    )

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mainViewModel = MainViewModel(repository)
    }

    @Test
    fun coroutines_TestReturnValueIsNotNull() {
        testCoroutineRule.runBlockingTest {
            val observer = Observer<ScreenState> {}
            val liveData = mainViewModel.liveData

            Mockito.`when`(repository.listUsersAsync()).thenReturn(
                listUser
            )

            try {
                liveData.observeForever(observer)
                mainViewModel.listUsers()
                Assert.assertNotNull(liveData.value)
            } finally {
                liveData.removeObserver(observer)
            }
        }
    }

    @Test
    fun coroutines_TestReturnValueIsError() {
        testCoroutineRule.runBlockingTest {
            val observer = Observer<ScreenState> {}
            val liveData = mainViewModel.liveData
            try {
                liveData.observeForever(observer)
                mainViewModel.listUsers()

                val value: ScreenState.Error = liveData.value as ScreenState.Error
                Assert.assertEquals(value.error.message, ERROR_TEXT)
            } finally {
                liveData.removeObserver(observer)
            }
        }
    }


    companion object {
        private const val ERROR_TEXT = "List users are null"
    }

}