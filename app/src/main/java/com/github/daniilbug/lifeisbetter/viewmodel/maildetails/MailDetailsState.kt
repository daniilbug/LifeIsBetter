package com.github.daniilbug.lifeisbetter.viewmodel.maildetails

import com.github.daniilbug.lifeisbetter.viewmodel.MailFeedBack

sealed class MailDetailsState {
    class FeedBack(val feedBack: MailFeedBack): MailDetailsState()
    object Loading: MailDetailsState()
}