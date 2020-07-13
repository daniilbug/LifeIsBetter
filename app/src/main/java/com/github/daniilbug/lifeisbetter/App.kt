package com.github.daniilbug.lifeisbetter

import android.app.Application
import com.github.daniilbug.auth.AuthProvider
import com.github.daniilbug.auth.UserSessionProvider
import com.github.daniilbug.data.MailsRepository
import com.github.daniilbug.data.UserRepository
import com.github.daniilbug.domain.interactor.*
import com.github.daniilbug.firebase_auth.FirebaseAuthProvider
import com.github.daniilbug.firebase_auth.FirebaseUserSessionProvider
import com.github.daniilbug.firebase_data.FirebaseMailRepository
import com.github.daniilbug.firebase_data.FirebaseUserRepository
import com.github.daniilbug.lifeisbetter.viewmodel.MailView
import com.github.daniilbug.lifeisbetter.viewmodel.maildetails.MailDetailsViewModel
import com.github.daniilbug.lifeisbetter.viewmodel.maillist.MailListViewModel
import com.github.daniilbug.lifeisbetter.viewmodel.profile.ProfileViewModel
import com.github.daniilbug.lifeisbetter.viewmodel.signin.SignInViewModel
import com.github.daniilbug.lifeisbetter.viewmodel.signup.SignUpViewModel
import com.github.daniilbug.lifeisbetter.viewmodel.writemail.WriteMailViewModel
import com.github.daniilbug.notifications.FirebaseNotificationSender
import com.github.daniilbug.notifications.FirebaseNotificationSubscriptionManager
import com.github.daniilbug.notifications.NotificationSender
import com.github.daniilbug.notifications.NotificationSubscriptionManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
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

        val notificationModule = module {
            single<NotificationSubscriptionManager> { FirebaseNotificationSubscriptionManager() }
            single<NotificationSender> { FirebaseNotificationSender() }
        }

        val interactorModule = module {
            factory { SignInInteractor(get(), get(), get()) }
            factory { SignUpInteractor(get(), get()) }
            factory { MailListInteractor(get(), get()) }
            factory { WriteMailInteractor(get(), get(), get(), get()) }
            factory { MailDetailsInteractor(get()) }
            factory { ProfileInteractor(get(), get(), get()) }
        }

        val viewModelModule = module {
            viewModel { SignInViewModel(get(), get()) }
            viewModel { SignUpViewModel(get(), get()) }
            viewModel { MailListViewModel(get()) }
            viewModel { WriteMailViewModel(get()) }
            viewModel { (mailView: MailView) -> MailDetailsViewModel(mailView, get()) }
            viewModel { ProfileViewModel(get()) }
        }

        startKoin {
            androidContext(this@App)
            modules(authModule, dataModule, notificationModule, interactorModule, viewModelModule)
        }
    }
}