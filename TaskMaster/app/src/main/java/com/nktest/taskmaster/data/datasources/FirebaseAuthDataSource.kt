package com.nktest.taskmaster.data.datasources

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.nktest.taskmaster.domain.entities.AuthResult
import com.nktest.taskmaster.domain.entities.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthDataSource {

    override suspend fun signIn(email: String, password: String): AuthResult {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val user = result.user?.toDomainUser()
            if (user != null) {
                AuthResult.Success(user)
            } else {
                AuthResult.Error("Sign in failed")
            }
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Sign in failed")
        }
    }

    override suspend fun signUp(email: String, password: String): AuthResult {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user?.toDomainUser()
            if (user != null) {
                AuthResult.Success(user)
            } else {
                AuthResult.Error("Sign up failed")
            }
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Sign up failed")
        }
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
    }

    override suspend fun getCurrentUser(): User? {
        return firebaseAuth.currentUser?.toDomainUser()
    }

    private fun FirebaseUser.toDomainUser(): User {
        return User(
            id = uid,
            email = email ?: ""
        )
    }
}

