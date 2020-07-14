package com.github.daniilbug.lifeisbetter.viewmodel.maillist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.github.daniilbug.data.Mail
import com.github.daniilbug.domain.interactor.MailListInteractor
import com.github.daniilbug.lifeisbetter.StringResolver
import com.github.daniilbug.lifeisbetter.viewmodel.MailFeedBack
import com.github.daniilbug.lifeisbetter.viewmodel.MailView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map
import java.lang.IllegalStateException
import java.util.*

@ExperimentalCoroutinesApi
class MailListViewModel(
    private val mailListInteractor: MailListInteractor
) : ViewModel() {
    object EmptyListException: Exception()

    val state = Pager(
        config = PagingConfig(pageSize = 20, initialLoadSize = 20),
        pagingSourceFactory = { createDataSource() })
        .flow
        .map { mails -> mails.map { mail -> mail.toMailView() } }
        .cachedIn(viewModelScope)

    private fun Mail.toMailView() =
        MailView(id, content, Date(date), MailFeedBack.fromNumber(feedback))

    private fun createDataSource() = object : PagingSource<Any, Mail>() {
        override suspend fun load(params: LoadParams<Any>): LoadResult<Any, Mail> {
            val page = params.key
            val pageSize = params.loadSize
            return try {
                loadDate(page, pageSize)
            } catch (ex: Exception) {
                LoadResult.Error(ex)
            }
        }
    }

    private suspend fun loadDate(page: Any?, pageSize: Int): PagingSource.LoadResult<Any, Mail> {
        val loadedData = mailListInteractor.getMailsByPage(page, pageSize)
        return if (loadedData.mails.isEmpty() && page == null) {
            PagingSource.LoadResult.Error(EmptyListException)
        } else {
            PagingSource.LoadResult.Page(
                data = loadedData.mails,
                nextKey = loadedData.nextPage,
                prevKey = null
            )
        }
    }
}
