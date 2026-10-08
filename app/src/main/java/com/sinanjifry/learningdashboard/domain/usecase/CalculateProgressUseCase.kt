package com.sinanjifry.learningdashboard.domain.usecase

class CalculateProgressUseCase {

    /**
     * Calculates integer percentage progress based on completed lessons vs total lessons.
     * Returns 0 if total lessons is 0 to avoid division by zero.
     */
    operator fun invoke(completedLessons: Int, totalLessons: Int): Int {
        if (totalLessons <= 0) return 0
        return (completedLessons * 100) / totalLessons
    }
}
