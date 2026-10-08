package com.sinanjifry.learningdashboard.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sinanjifry.learningdashboard.domain.repository.AuthRepository
import com.sinanjifry.learningdashboard.domain.repository.CourseRepository
import com.sinanjifry.learningdashboard.presentation.dashboard.DashboardScreen
import com.sinanjifry.learningdashboard.presentation.dashboard.DashboardViewModel
import com.sinanjifry.learningdashboard.presentation.dashboard.DashboardViewModelFactory
import com.sinanjifry.learningdashboard.presentation.details.CourseDetailsScreen
import com.sinanjifry.learningdashboard.presentation.details.CourseDetailsViewModel
import com.sinanjifry.learningdashboard.presentation.details.CourseDetailsViewModelFactory
import com.sinanjifry.learningdashboard.presentation.login.LoginScreen
import com.sinanjifry.learningdashboard.presentation.login.LoginViewModel
import com.sinanjifry.learningdashboard.presentation.login.LoginViewModelFactory

@Composable
fun NavGraph(
    authRepository: AuthRepository,
    courseRepository: CourseRepository,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Login.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.Login.route) {
            val viewModel: LoginViewModel = viewModel(
                factory = LoginViewModelFactory(authRepository)
            )
            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Screen.Dashboard.route) {
            val viewModel: DashboardViewModel = viewModel(
                factory = DashboardViewModelFactory(courseRepository)
            )
            DashboardScreen(
                viewModel = viewModel,
                onCourseClick = { courseId ->
                    navController.navigate(Screen.CourseDetails.createRoute(courseId))
                }
            )
        }

        composable(
            route = Screen.CourseDetails.route,
            arguments = listOf(
                navArgument("courseId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val courseId = backStackEntry.arguments?.getString("courseId") ?: ""
            val viewModel: CourseDetailsViewModel = viewModel(
                factory = CourseDetailsViewModelFactory(courseId, courseRepository)
            )
            CourseDetailsScreen(
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
