package com.github.daniilbug.lifeisbetter.viewmodel.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.daniilbug.domain.interactor.ProfileInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(private val profileInteractor: ProfileInteractor): ViewModel() {

    private val mutableState = MutableLiveData<ProfileState>()
    val state: LiveData<ProfileState> get() = mutableState

    init {
        loadStatistic()
    }

    private fun loadStatistic() = viewModelScope.launch(Dispatchers.IO) {
        val statistic = profileInteractor.getStatistic()
        mutableState.postValue(ProfileState.Statistic(statistic.mailsAmount, statistic.goodMailsAmount))
    }

    fun sendEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.LogOut -> logOut()
            is ProfileEvent.Refresh -> loadStatistic()
        }
    }

    private fun logOut() = viewModelScope.launch(Dispatchers.IO) {
        try {
            profileInteractor.logOut()
            mutableState.postValue(ProfileState.LogOut)
        } catch (ex: Exception) {

        }
    }
}