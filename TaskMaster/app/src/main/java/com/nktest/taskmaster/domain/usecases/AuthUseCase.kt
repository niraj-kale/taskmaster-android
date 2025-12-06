package com.nktest.taskmaster.domain.usecases

import com.nktest.taskmaster.domain.entities.AuthResult
import com.nktest.taskmaster.domain.entities.User

interface AuthUseCase {
    suspend fun signIn(email: String, password: String): AuthResult
    suspend fun signUp(email: String, password: String): AuthResult
    suspend fun signOut()
    suspend fun getCurrentUser(): User?
}

