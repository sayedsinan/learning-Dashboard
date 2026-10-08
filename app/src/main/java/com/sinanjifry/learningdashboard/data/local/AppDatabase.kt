package com.sinanjifry.learningdashboard.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sinanjifry.learningdashboard.data.local.dao.CourseDao
import com.sinanjifry.learningdashboard.data.local.dao.LessonDao
import com.sinanjifry.learningdashboard.data.local.entity.CourseEntity
import com.sinanjifry.learningdashboard.data.local.entity.LessonEntity

@Database(
    entities = [CourseEntity::class, LessonEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao
    abstract fun lessonDao(): LessonDao
}
