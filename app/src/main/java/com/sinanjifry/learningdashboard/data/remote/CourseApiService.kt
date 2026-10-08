package com.sinanjifry.learningdashboard.data.remote

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sinanjifry.learningdashboard.data.remote.model.CourseDto
import kotlinx.coroutines.delay

interface CourseApiService {
    suspend fun getCourses(): List<CourseDto>
}

class FakeCourseApiService(
    private val context: Context,
    private val gson: Gson = Gson()
) : CourseApiService {

    override suspend fun getCourses(): List<CourseDto> {
        delay(1500) // Simulate network delay
        val jsonString = context.assets.open("courses.json").bufferedReader().use { it.readText() }
        val type = object : TypeToken<List<CourseDto>>() {}.type
        return gson.fromJson(jsonString, type)
    }
}
