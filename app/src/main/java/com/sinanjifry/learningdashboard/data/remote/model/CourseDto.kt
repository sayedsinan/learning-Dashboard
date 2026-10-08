package com.sinanjifry.learningdashboard.data.remote.model

import com.google.gson.annotations.SerializedName

data class CourseDto(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("instructor") val instructor: String,
    @SerializedName("lessons") val lessons: List<LessonDto>
)

data class LessonDto(
    @SerializedName("id") val id: String,
    @SerializedName("courseId") val courseId: String,
    @SerializedName("title") val title: String,
    @SerializedName("isCompleted") val isCompleted: Boolean
)
