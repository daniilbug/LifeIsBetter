package com.github.daniilbug.firebase_auth

import com.github.daniilbug.auth.AuthProvider
import com.github.daniilbug.auth.AuthUser
import com.github.daniilbug.auth.exceptions.InvalidLoginOrPasswordException
import com.github.daniilbug.auth.exceptions.UserAlreadyExistsException
import com.github.daniilbug.auth.exceptions.WeakPasswordException
import com.github.daniilbug.auth.exceptions.WrongEmailFormatException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseAuthProvider : AuthProvider {
    private val firebaseAuth = Firebase.auth

    override suspend fun signIn(email: String, password: String) {
        try {
            firebaseAuth.signIn(email, password)
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            throw InvalidLoginOrPasswordException()
        } catch (e: FirebaseAuthInvalidUserException) {
            throw InvalidLoginOrPasswordException()
        }
    }

    override suspend fun signUp(email: String, password: String): AuthUser {
        try {
            val uid = firebaseAuth.signUp(email, password)
            return AuthUser(uid, email)
        } catch (e: FirebaseAuthUserCollisionException) {
            throw UserAlreadyExistsException()
        } catch (e: FirebaseAuthWeakPasswordException) {
            throw WeakPasswordException()
        } catch (e: FirebaseAuthEmailException) {
            throw WrongEmailFormatException()
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            throw WrongEmailFormatException()
        }
    }
}