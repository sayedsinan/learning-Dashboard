package com.sinanjifry.learningdashboard.presentation.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sinanjifry.learningdashboard.presentation.components.CourseCard
import com.sinanjifry.learningdashboard.presentation.components.EmptyView
import com.sinanjifry.learningdashboard.presentation.components.ErrorView
import com.sinanjifry.learningdashboard.presentation.components.LoadingView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onCourseClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Courses", style = MaterialTheme.typography.titleLarge) }
            )
        }
    ) { paddingValues ->
        when (val state = uiState) {
            is DashboardUiState.Loading -> {
                LoadingView(modifier = Modifier.padding(paddingValues))
            }
            is DashboardUiState.Empty -> {
                EmptyView(
                    message = "No courses available.",
                    modifier = Modifier.padding(paddingValues)
                )
            }
            is DashboardUiState.Error -> {
                ErrorView(
                    message = state.message,
                    onRetry = { viewModel.loadCourses() },
                    modifier = Modifier.padding(paddingValues)
                )
            }
            is DashboardUiState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(
                        items = state.courses,
                        key = { it.id }
                    ) { course ->
                        CourseCard(
                            course = course,
                            onContinueClick = { onCourseClick(course.id) }
                        )
                    }
                }
            }
        }
    }
}
