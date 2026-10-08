package com.sinanjifry.learningdashboard.domain.repository

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<Unit>
}
