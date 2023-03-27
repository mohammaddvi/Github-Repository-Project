@file:OptIn(ExperimentalAnimationApi::class)

package com.example.githubrepositories.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.example.githubrepositories.presentation.ProjectViewModel
import com.example.githubrepositories.presentation.composable.ProjectDetailsScreen
import com.example.githubrepositories.presentation.composable.ProjectListScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import org.koin.androidx.compose.getViewModel

sealed class ProjectScreen(val name: String) {
    object ProjectListScreen : ProjectScreen("ProjectListScreen")
    object ProjectDetailsScreen : ProjectScreen("ProjectDetailsScreen")
}

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavController() {
    val projectViewModel: ProjectViewModel = getViewModel()
    val navController = rememberAnimatedNavController()
    Scaffold {
        AnimatedNavHost(
            navController = navController,
            startDestination = ProjectScreen.ProjectListScreen.name
        ) {
            composable(
                route = ProjectScreen.ProjectListScreen.name,
            ) {
                ProjectListScreen(
                    viewModel = projectViewModel,
                    onRefresh = { projectViewModel.loadData() },
                    onUserClicked = {
                        navController.navigate(ProjectScreen.ProjectDetailsScreen.name)
                    })
            }
            composable(route = ProjectScreen.ProjectDetailsScreen.name) {
                ProjectDetailsScreen(project = projectViewModel.state.value.detailsProject!!)
            }
        }
    }
}

