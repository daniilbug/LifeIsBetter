package com.github.daniilbug.lifeisbetter.viewmodel.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.daniilbug.auth.exceptions.InvalidLoginOrPasswordException
import com.github.daniilbug.auth.exceptions.WrongEmailFormatException
import com.github.daniilbug.data.StringResolver
import com.github.daniilbug.domain.interactor.SignInInteractor
import com.github.daniilbug.lifeisbetter.R
import com.github.daniilbug.lifeisbetter.utils.StatusLiveData
import kotlinx.coroutines.*

@ExperimentalCoroutinesApi
class SignInViewModel(
    private val stringResolver: StringResolver,
    private val signInInteractor: SignInInteractor
) : ViewModel() {

    private val mutableState: MutableLiveData<SignInState> = MutableLiveData(SignInState.Default)
    private val mutableStatus: StatusLiveData<SignInStatus> = StatusLiveData()
    val state: LiveData<SignInState> get() = mutableState
    val status: LiveData<SignInStatus> get() = mutableStatus

    init {
        runBlocking {
            if (signInInteractor.isUserLoggedIn())
                mutableState.value = SignInState.Success
        }
    }

    fun sendEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.SignIn -> signIn(event.email, event.password)
        }
    }

    private fun signIn(email: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        mutableState.postValue(SignInState.Loading)
        try {
            signInInteractor.signIn(email, password)
            mutableState.postValue(SignInState.Success)
        } catch (e: InvalidLoginOrPasswordException) {
            sendError(R.string.invalid_login_or_password_error)
        } catch (e: WrongEmailFormatException) {
            sendError(R.string.wrong_email_format_error)
        } catch (e: Exception) {
            e.printStackTrace()
            mutableState.postValue(SignInState.Default)
        }
    }

    private fun sendError(messageId: Int) {
        mutableState.postValue(SignInState.Default)
        val message = stringResolver.getString(messageId)
        mutableStatus.postValue(SignInStatus.Error(message))
    }
}