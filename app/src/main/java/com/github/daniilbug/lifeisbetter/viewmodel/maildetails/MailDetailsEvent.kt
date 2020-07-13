package com.github.daniilbug.lifeisbetter.viewmodel.maildetails

import com.github.daniilbug.lifeisbetter.viewmodel.MailFeedBack

sealed class MailDetailsEvent {
    class ChangeFeedback(val feedBack: MailFeedBack): MailDetailsEvent()
}