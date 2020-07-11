package com.github.daniilbug.lifeisbetter.viewmodel.maillist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.github.daniilbug.data.Mail
import com.github.daniilbug.domain.interactor.MailListInteractor
import com.github.daniilbug.lifeisbetter.viewmodel.MailView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import java.util.*

@ExperimentalCoroutinesApi
class MailListViewModel(private val mailListInteractor: MailListInteractor): ViewModel() {

    val state = Pager(config = PagingConfig(pageSize = 10, initialLoadSize = 10), pagingSourceFactory = { createDataSource() })
        .flow
        .map { mails -> mails.map { mail -> mail.toMailView() } }
        .cachedIn(viewModelScope)

    private fun Mail.toMailView() = MailView(id, content, Date(date), feedback != -1)

    private fun createDataSource() = object: PagingSource<Int, Mail>() {
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Mail> {
            val page = params.key ?: 1
            val pageSize = params.loadSize
            return try {
                val nextPage: Int
                val data: List<Mail>
                if (page == -1) {
                    nextPage = -1
                    data = listOf()
                } else {
                    val loadedMails = mailListInteractor.getMails(page, pageSize)
                    data = loadedMails.mails
                    nextPage = loadedMails.nextPage
                }
                LoadResult.Page(
                    data = data ,
                    nextKey = nextPage,
                    prevKey = null
                )
            } catch (ex: Exception) {
                LoadResult.Error(ex)
            }
        }
    }
}