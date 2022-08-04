package com.gb.android.explorergithubtest

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gb.android.explorergithubtest.view.main.MainActivity
import com.gb.android.explorergithubtest.view.main.UsersAdapter
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityRecyclerViewTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun activitySearch_ScrollTo() {
        if (BuildConfig.TYPE == MainActivity.FAKE) {
            Espresso.onView(withId(R.id.users_recyclerView))
                .perform(
                    RecyclerViewActions.scrollTo<UsersAdapter.ViewHolder>(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("mojombo"))
                    )
                )
        }
    }

    @Test
    fun activitySearch_PerformClickAtPosition() {
        if (BuildConfig.TYPE == MainActivity.FAKE) {
            Espresso.onView(withId(R.id.users_recyclerView))
                .perform(
                    RecyclerViewActions.actionOnItemAtPosition<UsersAdapter.ViewHolder>(
                        0,
                        ViewActions.click()
                    )
                )
        }
    }

    @Test
    fun activitySearch_PerformClickOnItem() {
        if (BuildConfig.TYPE == MainActivity.FAKE) {

            Espresso.onView(withId(R.id.users_recyclerView))
                .perform(
                    RecyclerViewActions.scrollTo<UsersAdapter.ViewHolder>(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("defunkt"))
                    )
                )

            Espresso.onView(withId(R.id.users_recyclerView))
                .perform(
                    RecyclerViewActions.actionOnItem<UsersAdapter.ViewHolder>(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("pjhyett")),
                        ViewActions.click()
                    )
                )
        }
    }

    @After
    fun close() {
        scenario.close()
    }
}