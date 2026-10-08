package com.sinanjifry.learningdashboard.data.local.mapper

import com.sinanjifry.learningdashboard.data.local.entity.CourseEntity
import com.sinanjifry.learningdashboard.data.local.entity.LessonEntity
import com.sinanjifry.learningdashboard.domain.model.Course
import com.sinanjifry.learningdashboard.domain.model.Lesson

fun CourseEntity.toDomain(lessons: List<Lesson> = emptyList()): Course {
    return Course(
        id = id,
        title = title,
        instructor = instructor,
        progress = progress,
        lessonsCount = lessonsCount,
        lessons = lessons
    )
}

fun Course.toEntity(): CourseEntity {
    return CourseEntity(
        id = id,
        title = title,
        instructor = instructor,
        progress = progress,
        lessonsCount = lessonsCount
    )
}

fun LessonEntity.toDomain(): Lesson {
    return Lesson(
        id = id,
        courseId = courseId,
        title = title,
        isCompleted = isCompleted
    )
}

fun Lesson.toEntity(): LessonEntity {
    return LessonEntity(
        id = id,
        courseId = courseId,
        title = title,
        isCompleted = isCompleted
    )
}
