package com.github.daniilbug.lifeisbetter

import android.app.Application
import com.github.daniilbug.auth.AuthProvider
import com.github.daniilbug.auth.UserSessionProvider
import com.github.daniilbug.data.MailsRepository
import com.github.daniilbug.data.StringResolver
import com.github.daniilbug.data.UserRepository
import com.github.daniilbug.domain.interactor.MailListInteractor
import com.github.daniilbug.domain.interactor.SignInInteractor
import com.github.daniilbug.domain.interactor.SignUpInteractor
import com.github.daniilbug.domain.interactor.WriteMailInteractor
import com.github.daniilbug.firebase_auth.FirebaseAuthProvider
import com.github.daniilbug.firebase_auth.FirebaseUserSession
import com.github.daniilbug.firebase_auth.FirebaseUserSessionProvider
import com.github.daniilbug.firebase_data.FirebaseMailRepository
import com.github.daniilbug.firebase_data.FirebaseUserRepository
import com.github.daniilbug.lifeisbetter.adapter.MailListAdapter
import com.github.daniilbug.lifeisbetter.viewmodel.maillist.MailListViewModel
import com.github.daniilbug.lifeisbetter.viewmodel.signin.SignInViewModel
import com.github.daniilbug.lifeisbetter.viewmodel.signup.SignUpViewModel
import com.github.daniilbug.lifeisbetter.viewmodel.writemail.WriteMailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.koinApplication
import org.koin.dsl.module

class App : Application() {
    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onCreate() {
        super.onCreate()
        val authModule = module {
            single<AuthProvider> { FirebaseAuthProvider() }
            single<UserSessionProvider> { FirebaseUserSessionProvider() }
        }

        val dataModule = module {
            single<StringResolver> { ResourceStringResolver(get()) }
            single<MailsRepository> { FirebaseMailRepository() }
            single<UserRepository> { FirebaseUserRepository() }
        }

        val interactorModule = module {
            factory { SignInInteractor(get(), get()) }
            factory { SignUpInteractor(get(), get()) }
            factory { MailListInteractor(get()) }
            factory { WriteMailInteractor(get(), get(), get()) }
        }

        val viewModelModule = module {
            viewModel { SignInViewModel(get(), get()) }
            viewModel { SignUpViewModel(get(), get()) }
            viewModel { MailListViewModel(get()) }
            viewModel { WriteMailViewModel(get()) }
        }

        startKoin {
            androidContext(this@App)
            modules(authModule, dataModule, interactorModule, viewModelModule)
        }
    }
}