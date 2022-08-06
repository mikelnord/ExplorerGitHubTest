package com.gb.android.explorergithubtest.view.main

import androidx.lifecycle.*
import com.gb.android.explorergithubtest.model.User
import com.gb.android.explorergithubtest.repository.IGitHubRepository
import kotlinx.coroutines.*

internal class MainViewModel(
    private val repository: IGitHubRepository
) : ViewModel() {

    init {
        listUsers()
    }

    private val _liveData = MutableLiveData<ScreenState>()
    val liveData: LiveData<ScreenState> = _liveData

    fun listUsers() {
        viewModelScope.launch {
            val listUsers = repository.listUsersAsync()
            if (listUsers != null) {
                _liveData.postValue(ScreenState.Working(listUsers))
            } else {
                _liveData.postValue(ScreenState.Error(Throwable("List users are null")))
            }
        }
    }

}

sealed class ScreenState {
    object Loading : ScreenState()
    data class Working(val list: List<User>?) : ScreenState()
    data class Error(val error: Throwable) : ScreenState()
}

internal class MyViewModelFactory constructor(private val repository: IGitHubRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}