package com.gb.android.explorergithubtest.presenter.detail

import com.gb.android.explorergithubtest.presenter.PresenterContract

internal interface PresenterDetailsContract:PresenterContract  {
    fun setCounter(count: Int)
    fun onIncrement()
    fun onDecrement()
}