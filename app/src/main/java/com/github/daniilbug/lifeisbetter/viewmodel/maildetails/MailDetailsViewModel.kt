package com.github.daniilbug.lifeisbetter.viewmodel.maildetails

import androidx.lifecycle.*
import com.github.daniilbug.domain.interactor.MailDetailsInteractor
import com.github.daniilbug.lifeisbetter.utils.StatusLiveData
import com.github.daniilbug.lifeisbetter.viewmodel.MailFeedBack
import com.github.daniilbug.lifeisbetter.viewmodel.MailView
import com.github.daniilbug.lifeisbetter.viewmodel.toNumber
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class MailDetailsViewModel(
    private val mail: MailView,
    private val mailDetailsInteractor: MailDetailsInteractor
) : ViewModel() {

    val state: LiveData<MailDetailsState> = mailDetailsInteractor.getFeedBack(mail.id)
        .map { feedback -> feedBackToState(feedback) }
        .onStart { emit(MailDetailsState.Loading) }
        .flowOn(Dispatchers.IO)
        .asLiveData(viewModelScope.coroutineContext)

    private val mutableStatus = StatusLiveData<MailDetailsStatus>()
    val status: LiveData<MailDetailsStatus> get() = mutableStatus
    private val feedbackChanges = BroadcastChannel<MailFeedBack>(128)

    init {
        viewModelScope.launch {
            feedbackChanges.asFlow().collectLatest { feedBack ->
                changeFeedback(feedBack)
            }
        }
    }

    fun sendEvent(event: MailDetailsEvent) {
        when (event) {
            is MailDetailsEvent.ChangeFeedback -> viewModelScope.launch { feedbackChanges.send(event.feedBack) }
        }
    }

    private fun changeFeedback(feedBack: MailFeedBack) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val feedbackNumber = feedBack.toNumber()
            mailDetailsInteractor.setFeedBack(mail.id, feedbackNumber)
        } catch (e: Exception) {
            mutableStatus.postValue(MailDetailsStatus.Error(e.localizedMessage ?: ""))
        }
    }

    private fun feedBackToState(feedbackNumber: Int): MailDetailsState {
        return MailDetailsState.FeedBack(MailFeedBack.fromNumber(feedbackNumber))
    }
}