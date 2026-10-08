package com.sinanjifry.learningdashboard.data.repository

import com.sinanjifry.learningdashboard.domain.repository.AuthRepository
import kotlinx.coroutines.delay

class AuthRepositoryImpl : AuthRepository {
    override suspend fun login(email: String, password: String): Result<Unit> {
        delay(1000)
        return if (email == "test@test.com" && password == "123456") {
            Result.success(Unit)
        } else {
            Result.failure(Exception("Invalid Credentials."))
        }
    }
}
