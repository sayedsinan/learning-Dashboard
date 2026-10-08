package com.sinanjifry.learningdashboard.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sinanjifry.learningdashboard.domain.repository.CourseRepository

class CourseDetailsViewModelFactory(
    private val courseId: String,
    private val courseRepository: CourseRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CourseDetailsViewModel::class.java)) {
            return CourseDetailsViewModel(courseId, courseRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
