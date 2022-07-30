package com.gb.android.automator

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class BehaviorTest {

    private val uiDevice: UiDevice =
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val packageName = context.packageName

    @Before
    fun setup() {
        uiDevice.pressHome()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
        uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), TIMEOUT)
    }

    @Test
    fun test_MainActivityIsStarted() {
        val editText = uiDevice.findObject(By.res(packageName, "totalCountTextView"))
        Assert.assertNotNull(editText)
    }


    @Test
    fun test_OpenDetailsScreen() {
        val toDetails: UiObject2 = uiDevice.findObject(
            By.res(
                packageName,
                "toDetailsActivityButton"
            )
        )
        toDetails.click()

        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "detailTotalCountTextView")),
                TIMEOUT
            )
        Assert.assertEquals(changedText.text, "Number of results: 30")
    }

    @Test
    fun test_click_incrementButton() {
        val toDetails: UiObject2 = uiDevice.findObject(
            By.res(
                packageName,
                "toDetailsActivityButton"
            )
        )
        Assert.assertNotNull(toDetails)
        toDetails.click()
        val toIncrement =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "incrementButton")),
                TIMEOUT
            )
        Assert.assertNotNull(toIncrement)
        toIncrement.click()
        val changedText: UiObject2 =
            uiDevice.findObject(By.res(packageName, "detailTotalCountTextView"))
        Assert.assertNotNull(changedText)
        Assert.assertEquals(changedText.text, "Number of results: 31")
    }

    @Test
    fun test_click_decrementButton() {
        val toDetails: UiObject2 = uiDevice.findObject(
            By.res(
                packageName,
                "toDetailsActivityButton"
            )
        )
        Assert.assertNotNull(toDetails)
        toDetails.click()
        val toDecrement =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "decrementButton")),
                TIMEOUT
            )
        Assert.assertNotNull(toDecrement)
        toDecrement.click()
        val changedText: UiObject2 =
            uiDevice.findObject(By.res(packageName, "detailTotalCountTextView"))
        Assert.assertNotNull(changedText)
        Assert.assertEquals(changedText.text, "Number of results: 29")
    }

    companion object {
        private const val TIMEOUT = 5000L
    }
}
