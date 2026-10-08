package com.sinanjifry.learningdashboard.data.repository

import com.sinanjifry.learningdashboard.data.local.dao.CourseDao
import com.sinanjifry.learningdashboard.data.local.dao.LessonDao
import com.sinanjifry.learningdashboard.data.local.entity.CourseEntity
import com.sinanjifry.learningdashboard.data.local.entity.LessonEntity
import com.sinanjifry.learningdashboard.data.local.mapper.toDomain
import com.sinanjifry.learningdashboard.data.remote.CourseApiService
import com.sinanjifry.learningdashboard.domain.model.Course
import com.sinanjifry.learningdashboard.domain.repository.CourseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class CourseRepositoryImpl(
    private val apiService: CourseApiService,
    private val courseDao: CourseDao,
    private val lessonDao: LessonDao
) : CourseRepository {

    var forceNetworkFailure: Boolean = false // Toggle for testing offline mode fallback

    override fun getCourses(): Flow<Result<List<Course>>> = flow {
        // Attempt remote fetch
        try {
            if (forceNetworkFailure) throw Exception("Network error")
            val remoteDtos = apiService.getCourses()
            
            // Cache to database
            remoteDtos.forEach { dto ->
                val totalLessons = dto.lessons.size
                val completedLessons = dto.lessons.count { it.isCompleted }
                val progressPercent = if (totalLessons > 0) (completedLessons * 100) / totalLessons else 0

                val courseEntity = CourseEntity(
                    id = dto.id,
                    title = dto.title,
                    instructor = dto.instructor,
                    progress = progressPercent,
                    lessonsCount = totalLessons
                )
                courseDao.insertCourse(courseEntity)

                val lessonEntities = dto.lessons.map { lessonDto ->
                    LessonEntity(
                        id = lessonDto.id,
                        courseId = dto.id,
                        title = lessonDto.title,
                        isCompleted = lessonDto.isCompleted
                    )
                }
                lessonDao.insertLessons(lessonEntities)
            }
        } catch (e: Exception) {
            // Network failed or offline - fall back to Room
        }

        // Emit from database as single source of truth
        courseDao.getAllCourses().collect { entities ->
            if (entities.isEmpty()) {
                emit(Result.failure(Exception("No cached data available.")))
            } else {
                val courses = entities.map { entity -> entity.toDomain() }
                emit(Result.success(courses))
            }
        }
    }

    override fun getCourseDetails(courseId: String): Flow<Result<Course>> {
        return combine(
            courseDao.getCourseById(courseId),
            lessonDao.getLessonsForCourse(courseId)
        ) { courseEntity, lessonEntities ->
            if (courseEntity == null) {
                Result.failure(Exception("Course not found"))
            } else {
                val lessons = lessonEntities.map { it.toDomain() }
                Result.success(courseEntity.toDomain(lessons))
            }
        }
    }

    override suspend fun toggleLessonCompletion(
        courseId: String,
        lessonId: String,
        isCompleted: Boolean
    ): Result<Unit> {
        return try {
            lessonDao.updateLessonCompletion(lessonId, isCompleted)

            // Recalculate progress dynamically
            val total = lessonDao.getTotalLessonsCount(courseId)
            val completed = lessonDao.getCompletedLessonsCount(courseId)
            val progressPercent = if (total > 0) (completed * 100) / total else 0

            // Update course in Room
            courseDao.updateCourseProgress(courseId, progressPercent)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
