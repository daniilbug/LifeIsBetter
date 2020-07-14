package com.github.daniilbug.lifeisbetter.viewmodel.writemail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.daniilbug.data.BlankMailException
import com.github.daniilbug.domain.interactor.WriteMailInteractor
import com.github.daniilbug.lifeisbetter.R
import com.github.daniilbug.lifeisbetter.StringResolver
import com.github.daniilbug.lifeisbetter.utils.StatusLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WriteMailViewModel(
    private val stringResolver: StringResolver,
    private val writeMailInteractor: WriteMailInteractor
) : ViewModel() {

    private val mutableStatus = StatusLiveData<WriteMailStatus>()
    val status: LiveData<WriteMailStatus> get() = mutableStatus

    fun sendEvent(event: WriteMailEvent) {
        when (event) {
            is WriteMailEvent.SendMail -> sendMail(event.text)
        }
    }

    private fun sendMail(text: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            writeMailInteractor.sendMail(text)
            mutableStatus.postValue(WriteMailStatus.Success)
        } catch (e: BlankMailException) {
            val errorMessage = stringResolver.getString(R.string.blank_message)
            mutableStatus.postValue(WriteMailStatus.Error(errorMessage))
        }
    }
}