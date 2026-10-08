package com.sinanjifry.learningdashboard.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class CourseEntity(
    @PrimaryKey val id: String,
    val title: String,
    val instructor: String,
    val progress: Int,
    val lessonsCount: Int
)
