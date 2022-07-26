package com.gb.android.explorergithubtest.presenter.detail

internal interface PresenterDetailsContract  {
    fun setCounter(count: Int)
    fun onIncrement()
    fun onDecrement()
}