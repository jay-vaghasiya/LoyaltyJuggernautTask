package com.jay.loyaltyjuggernauttask.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.jay.loyaltyjuggernauttask.ui.theme.LoyaltyJuggernautTaskTheme
import com.jay.loyaltyjuggernauttask.view.screen.Landing
import com.jay.loyaltyjuggernauttask.view.screen.LandingScreen
import com.jay.loyaltyjuggernauttask.view.screen.Web
import com.jay.loyaltyjuggernauttask.view.screen.WebScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoyaltyJuggernautTaskTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Landing,
                        enterTransition = {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Start,
                                tween(700)
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Start,
                                tween(700)
                            )
                        },
                        popEnterTransition = {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.End,
                                tween(700)
                            )
                        },
                        popExitTransition = {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.End,
                                tween(700)
                            )
                        },

                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        composable<Landing> {
                            LandingScreen(navController)
                        }
                        composable<Web> {
                            val args = it.toRoute<Web>()
                            WebScreen(args.url)
                        }
                    }
                }
            }
        }
    }
}



