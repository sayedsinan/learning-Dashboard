package com.sinanjifry.learningdashboard.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CalculateProgressUseCaseTest {

    private lateinit var calculateProgressUseCase: CalculateProgressUseCase

    @Before
    fun setUp() {
        calculateProgressUseCase = CalculateProgressUseCase()
    }

    @Test
    fun `calculate progress when 2 out of 4 lessons completed returns 50 percent`() {
        // Arrange
        val completed = 2
        val total = 4

        // Act
        val result = calculateProgressUseCase(completed, total)

        // Assert
        assertEquals(50, result)
    }

    @Test
    fun `calculate progress when zero total lessons returns zero percent`() {
        val result = calculateProgressUseCase(0, 0)
        assertEquals(0, result)
    }

    @Test
    fun `calculate progress when all lessons completed returns 100 percent`() {
        val result = calculateProgressUseCase(5, 5)
        assertEquals(100, result)
    }
}
