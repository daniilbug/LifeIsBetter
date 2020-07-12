package com.github.daniilbug.firebase_auth

import com.github.daniilbug.auth.*
import com.github.daniilbug.auth.exceptions.InvalidLoginOrPasswordException
import com.github.daniilbug.auth.exceptions.UserAlreadyExistsException
import com.github.daniilbug.auth.exceptions.WeakPasswordException
import com.github.daniilbug.auth.exceptions.WrongEmailFormatException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseAuthProvider: AuthProvider {
    private val firebaseAuth = Firebase.auth

    override suspend fun signIn(email: String, password: String) {
        try {
            val uid = firebaseAuth.signIn(email, password)
            Firebase.database.getReference("users").child(uid).setValue(User(email))
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            throw InvalidLoginOrPasswordException()
        } catch (e: FirebaseAuthInvalidUserException) {
            throw InvalidLoginOrPasswordException()
        }
    }

    override suspend fun signUp(email: String, password: String) {
        try {
            firebaseAuth.signUp(email, password)
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