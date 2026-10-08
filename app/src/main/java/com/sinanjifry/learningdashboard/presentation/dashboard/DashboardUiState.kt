package com.sinanjifry.learningdashboard.presentation.dashboard

import com.sinanjifry.learningdashboard.domain.model.Course

sealed interface DashboardUiState {
    object Loading : DashboardUiState
    object Empty : DashboardUiState
    data class Success(val courses: List<Course>) : DashboardUiState
    data class Error(val message: String) : DashboardUiState
}
