package com.github.daniilbug.lifeisbetter.viewmodel.maildetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.github.daniilbug.domain.interactor.MailDetailsInteractor
import com.github.daniilbug.lifeisbetter.viewmodel.MailFeedBack
import com.github.daniilbug.lifeisbetter.viewmodel.MailView
import com.github.daniilbug.lifeisbetter.viewmodel.toNumber
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
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

    private val feedbackChanges = MutableStateFlow(mail.feedBack)

    init {
        viewModelScope.launch {
            feedbackChanges.collectLatest { feedBack ->
                changeFeedback(feedBack)
            }
        }
    }

    fun sendEvent(event: MailDetailsEvent) {
        when (event) {
            is MailDetailsEvent.ChangeFeedback -> feedbackChanges.value = event.feedBack
        }
    }

    private fun changeFeedback(feedBack: MailFeedBack) = viewModelScope.launch(Dispatchers.IO) {
        val feedbackNumber = feedBack.toNumber()
        mailDetailsInteractor.setFeedBack(mail.id, feedbackNumber)
    }

    private fun feedBackToState(feedbackNumber: Int): MailDetailsState {
        return MailDetailsState.FeedBack(MailFeedBack.fromNumber(feedbackNumber))
    }
}