package com.example.githubrepositories.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

val LocalNavigation = compositionLocalOf<NavHostController> { error("Navigation not set") }

@ExperimentalAnimationApi
@Composable
fun ProvideNavigation(
    navHostController: NavHostController = rememberAnimatedNavController(),
    content: @Composable (NavHostController) -> Unit

) {
    CompositionLocalProvider(LocalNavigation provides navHostController) {
        content(navHostController)
    }
}