package com.sinanjifry.learningdashboard.presentation.details

import com.sinanjifry.learningdashboard.domain.model.Course

sealed interface DetailsUiState {
    object Loading : DetailsUiState
    data class Success(val course: Course) : DetailsUiState
    data class Error(val message: String) : DetailsUiState
}
