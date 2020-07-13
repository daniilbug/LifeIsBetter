package com.github.daniilbug.lifeisbetter.viewmodel.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.daniilbug.auth.exceptions.ConfirmPasswordIsWrongException
import com.github.daniilbug.auth.exceptions.UserAlreadyExistsException
import com.github.daniilbug.auth.exceptions.WeakPasswordException
import com.github.daniilbug.auth.exceptions.WrongEmailFormatException
import com.github.daniilbug.lifeisbetter.StringResolver
import com.github.daniilbug.domain.interactor.SignUpInteractor
import com.github.daniilbug.lifeisbetter.R
import com.github.daniilbug.lifeisbetter.utils.StatusLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val stringResolver: StringResolver,
    private val signUpInteractor: SignUpInteractor
) : ViewModel() {
    private val mutableState: MutableLiveData<SignUpState> = MutableLiveData(SignUpState.Default)
    private val mutableStatus: StatusLiveData<SignUpStatus> = StatusLiveData()
    val state: LiveData<SignUpState> get() = mutableState
    val status: LiveData<SignUpStatus> get() = mutableStatus

    fun sendEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.SignUp -> signUp(event.email, event.password, event.confirmPassword)
        }
    }

    private fun signUp(email: String, password: String, confirmPassword: String) =
        viewModelScope.launch(Dispatchers.IO) {
            mutableState.postValue(SignUpState.Loading)
            try {
                signUpInteractor.signUp(email, password, confirmPassword)
                mutableState.postValue(SignUpState.Success)
            } catch (e: UserAlreadyExistsException) {
                sendError(R.string.user_exists_error)
            } catch (e: WrongEmailFormatException) {
                sendError(R.string.wrong_email_format_error)
            } catch (e: WeakPasswordException) {
                sendError(R.string.weak_password_error)
            } catch (e: ConfirmPasswordIsWrongException) {
                sendError(R.string.confirm_password_error)
            } catch (e: Exception) {
                e.printStackTrace()
                mutableState.postValue(SignUpState.Default)
            }
        }

    private fun sendError(messageId: Int) {
        mutableState.postValue(SignUpState.Default)
        val message = stringResolver.getString(messageId)
        mutableStatus.postValue(SignUpStatus.Error(message))
    }
}