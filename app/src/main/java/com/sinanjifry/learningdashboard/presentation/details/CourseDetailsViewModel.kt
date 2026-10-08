package com.sinanjifry.learningdashboard.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sinanjifry.learningdashboard.domain.repository.CourseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CourseDetailsViewModel(
    private val courseId: String,
    private val courseRepository: CourseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    init {
        loadCourseDetails()
    }

    fun loadCourseDetails() {
        viewModelScope.launch {
            _uiState.value = DetailsUiState.Loading
            courseRepository.getCourseDetails(courseId).collect { result ->
                result.onSuccess { course ->
                    _uiState.value = DetailsUiState.Success(course)
                }.onFailure { error ->
                    _uiState.value = DetailsUiState.Error(error.message ?: "Failed to load course details")
                }
            }
        }
    }

    fun toggleLesson(lessonId: String, isCompleted: Boolean) {
        viewModelScope.launch {
            courseRepository.toggleLessonCompletion(courseId, lessonId, isCompleted)
        }
    }
}
