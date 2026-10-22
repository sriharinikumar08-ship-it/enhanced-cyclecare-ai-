package com.simats.enhancedcyclecareai.ui.navigation

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.simats.enhancedcyclecareai.ui.home.HomeScreen
import com.simats.enhancedcyclecareai.ui.login.LoginScreen
import com.simats.enhancedcyclecareai.ui.profile.HealthProfileScreen
import com.simats.enhancedcyclecareai.ui.register.RegisterScreen
import com.simats.enhancedcyclecareai.ui.splash.SplashScreen
import com.simats.enhancedcyclecareai.ui.splash.SplashUiState
import com.simats.enhancedcyclecareai.ui.splash.SplashViewModel
import com.simats.enhancedcyclecareai.ui.welcome.WelcomeScreen

sealed interface NavDestination {
    data object Splash : NavDestination
    data object Welcome : NavDestination
    data object Register : NavDestination
    data object Login : NavDestination
    data object HealthProfile : NavDestination
    data object Home : NavDestination
}

@Composable
fun AppNavigation(
    splashViewModel: SplashViewModel = viewModel()
) {
    val splashState by splashViewModel.uiState.collectAsState()
    var currentDestination by remember { mutableStateOf<NavDestination>(NavDestination.Splash) }

    if (splashState is SplashUiState.Completed && currentDestination == NavDestination.Splash) {
        currentDestination = NavDestination.Welcome
    }

    Crossfade(
        targetState = currentDestination,
        animationSpec = tween(durationMillis = 500),
        label = "ScreenTransition"
    ) { destination ->
        when (destination) {
            NavDestination.Splash -> {
                SplashScreen()
            }
            NavDestination.Welcome -> {
                WelcomeScreen(
                    onGetStartedClick = {
                        currentDestination = NavDestination.Register
                    },
                    onLoginClick = {
                        currentDestination = NavDestination.Login
                    }
                )
            }
            NavDestination.Register -> {
                RegisterScreen(
                    onNavigateBack = {
                        currentDestination = NavDestination.Welcome
                    },
                    onNavigateToHome = {
                        currentDestination = NavDestination.HealthProfile
                    },
                    onNavigateToLogin = {
                        currentDestination = NavDestination.Login
                    }
                )
            }
            NavDestination.Login -> {
                LoginScreen(
                    onNavigateBack = {
                        currentDestination = NavDestination.Welcome
                    },
                    onNavigateToHome = {
                        currentDestination = NavDestination.HealthProfile
                    },
                    onNavigateToRegister = {
                        currentDestination = NavDestination.Register
                    }
                )
            }
            NavDestination.HealthProfile -> {
                HealthProfileScreen(
                    onNavigateBack = {
                        currentDestination = NavDestination.Register
                    },
                    onNavigateNext = {
                        currentDestination = NavDestination.Home
                    }
                )
            }
            NavDestination.Home -> {
                HomeScreen(
                    onNavigateBack = {
                        currentDestination = NavDestination.HealthProfile
                    }
                )
            }
        }
    }
}
