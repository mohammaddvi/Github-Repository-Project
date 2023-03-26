package com.example.githubrepositories.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.core.view.WindowCompat
import com.example.githubrepositories.presentation.navigation.NavController
import com.example.githubrepositories.presentation.navigation.ProvideNavigation
import org.koin.core.component.KoinComponent

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ProvideNavigation {
                NavController()
            }
        }
    }
}