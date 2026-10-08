package com.sinanjifry.learningdashboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.sinanjifry.learningdashboard.data.local.AppDatabase
import com.sinanjifry.learningdashboard.data.remote.FakeCourseApiService
import com.sinanjifry.learningdashboard.data.repository.AuthRepositoryImpl
import com.sinanjifry.learningdashboard.data.repository.CourseRepositoryImpl
import com.sinanjifry.learningdashboard.navigation.NavGraph
import com.sinanjifry.learningdashboard.ui.theme.LearningDashboardTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Local Room Database
        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "learning_dashboard.db"
        ).build()

        // Initialize Remote API & Repositories
        val apiService = FakeCourseApiService(applicationContext)
        val authRepository = AuthRepositoryImpl()
        val courseRepository = CourseRepositoryImpl(
            apiService = apiService,
            courseDao = database.courseDao(),
            lessonDao = database.lessonDao()
        )

        setContent {
            LearningDashboardTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGraph(
                        authRepository = authRepository,
                        courseRepository = courseRepository
                    )
                }
            }
        }
    }
}