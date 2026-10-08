package com.sinanjifry.learningdashboard.domain.repository

import com.sinanjifry.learningdashboard.domain.model.Course
import com.sinanjifry.learningdashboard.domain.model.Lesson
import kotlinx.coroutines.flow.Flow

interface CourseRepository {
    fun getCourses(): Flow<Result<List<Course>>>
    fun getCourseDetails(courseId: String): Flow<Result<Course>>
    suspend fun toggleLessonCompletion(courseId: String, lessonId: String, isCompleted: Boolean): Result<Unit>
}
