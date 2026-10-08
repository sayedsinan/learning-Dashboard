package com.sinanjifry.learningdashboard.domain.model

data class Course(
    val id: String,
    val title: String,
    val instructor: String,
    val progress: Int,
    val lessonsCount: Int,
    val lessons: List<Lesson> = emptyList()
)
