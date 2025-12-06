package com.nktest.taskmaster.data.datasources

import com.nktest.taskmaster.domain.entities.AuthResult
import com.nktest.taskmaster.domain.entities.User

interface AuthDataSource {
    suspend fun signIn(email: String, password: String): AuthResult
    suspend fun signUp(email: String, password: String): AuthResult
    suspend fun signOut()
    suspend fun getCurrentUser(): User?
}

