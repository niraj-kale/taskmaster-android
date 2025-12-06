package com.nktest.taskmaster.data.repositories

import com.nktest.taskmaster.data.datasources.AuthDataSource
import com.nktest.taskmaster.domain.entities.AuthResult
import com.nktest.taskmaster.domain.entities.User
import com.nktest.taskmaster.domain.repositories.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {

    override suspend fun signIn(email: String, password: String): AuthResult {
        return authDataSource.signIn(email, password)
    }

    override suspend fun signUp(email: String, password: String): AuthResult {
        return authDataSource.signUp(email, password)
    }

    override suspend fun signOut() {
        authDataSource.signOut()
    }

    override suspend fun getCurrentUser(): User? {
        return authDataSource.getCurrentUser()
    }
}

