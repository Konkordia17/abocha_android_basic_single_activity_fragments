package com.example.cupcake

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cupcake.model.OrderViewModel

@Composable
fun AppNavHost(viewModel: OrderViewModel, doOnSend: () -> Unit) {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = NavigationItem.Start.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }) {
        composable(route = NavigationItem.Start.route) {
            StartScreen(navController = navController, viewModel = viewModel)
        }

        composable(
            route = NavigationItem.Flavor.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }

        ) {
            FlavorScreen(viewModel = viewModel, navController = navController)
        }

        composable(route = NavigationItem.PickUp.route,
            enterTransition = {
                slideInVertically(
                    animationSpec = tween(
                        500, easing = FastOutSlowInEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(500, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Up
                )
            },
            exitTransition = {
                slideOutVertically(
                    animationSpec = tween(
                        500, easing = FastOutSlowInEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(500, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.Down
                )
            }) {
            PickUpScreen(viewModel = viewModel, navController = navController)
        }
        composable(
            route = NavigationItem.Summary.route,
            enterTransition = {
                scaleIn(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    ),
                    initialScale = 0.5f
                ) + fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                scaleOut(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutLinearInEasing
                    ),
                    targetScale = 0.5f
                ) + fadeOut(animationSpec = tween(300))
            }
        ) {
            SummaryScreen(viewModel = viewModel, doOnSend = doOnSend, navController = navController)
        }
    }
}
