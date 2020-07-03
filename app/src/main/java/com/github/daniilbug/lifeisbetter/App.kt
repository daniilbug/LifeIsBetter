package com.github.daniilbug.lifeisbetter

import android.app.Application
import com.github.daniilbug.auth.AuthProvider
import com.github.daniilbug.auth.UserSessionProvider
import com.github.daniilbug.domain.interactor.MainInteractor
import com.github.daniilbug.domain.interactor.SignInInteractor
import com.github.daniilbug.domain.interactor.SignUpInteractor
import com.github.daniilbug.firebase_auth.FirebaseAuthProvider
import com.github.daniilbug.firebase_auth.FirebaseUserSessionProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.koinApplication
import org.koin.dsl.module

class App : Application() {
    @ExperimentalCoroutinesApi
    override fun onCreate() {
        super.onCreate()
        val authModule = module {
            single<AuthProvider> { FirebaseAuthProvider() }
            single<UserSessionProvider> { FirebaseUserSessionProvider() }
        }

        val interactorModule = module {
            factory { SignInInteractor(get()) }
            factory { SignUpInteractor(get()) }
            factory { MainInteractor(get()) }
        }

        koinApplication {
            modules(authModule, interactorModule)
        }
    }
}