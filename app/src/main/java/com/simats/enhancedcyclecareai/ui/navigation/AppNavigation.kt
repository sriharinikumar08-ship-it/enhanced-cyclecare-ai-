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
import com.simats.enhancedcyclecareai.ui.analysis.AiHealthAnalysisScreen
import com.simats.enhancedcyclecareai.ui.cycle.CycleTrackingScreen
import com.simats.enhancedcyclecareai.ui.cycledetails.CycleDetailsScreen
import com.simats.enhancedcyclecareai.ui.dietary.DietaryPreferencesScreen
import com.simats.enhancedcyclecareai.ui.home.HomeScreen
import com.simats.enhancedcyclecareai.ui.lifestyle.LifestyleTrackingScreen
import com.simats.enhancedcyclecareai.ui.login.LoginScreen
import com.simats.enhancedcyclecareai.ui.menstrualpattern.GeneralMenstrualPatternScreen
import com.simats.enhancedcyclecareai.ui.patterndetection.PatternDetectionScreen
import com.simats.enhancedcyclecareai.ui.personalizedguidance.PersonalizedGuidanceScreen
import com.simats.enhancedcyclecareai.ui.personalizedmeal.PersonalizedMealScreen
import com.simats.enhancedcyclecareai.ui.personalizednutrition.PersonalizedNutritionScreen
import com.simats.enhancedcyclecareai.ui.pcospattern.PcosRelatedPatternScreen
import com.simats.enhancedcyclecareai.ui.preprocessing.DataPreprocessingScreen
import com.simats.enhancedcyclecareai.ui.profile.HealthProfileScreen
import com.simats.enhancedcyclecareai.ui.register.RegisterScreen
import com.simats.enhancedcyclecareai.ui.splash.SplashScreen
import com.simats.enhancedcyclecareai.ui.splash.SplashUiState
import com.simats.enhancedcyclecareai.ui.splash.SplashViewModel
import com.simats.enhancedcyclecareai.ui.summary.DataSummaryScreen
import com.simats.enhancedcyclecareai.ui.symptoms.SymptomTrackingScreen
import com.simats.enhancedcyclecareai.ui.welcome.WelcomeScreen
import com.simats.enhancedcyclecareai.ui.foodalternatives.FoodAlternativesScreen
import com.simats.enhancedcyclecareai.ui.screendir.ScreenDirectoryScreen
import com.simats.enhancedcyclecareai.ui.mealdetails.MealDetailsScreen
import com.simats.enhancedcyclecareai.ui.mealfeedback.MealFeedbackScreen
import com.simats.enhancedcyclecareai.ui.meallog.MealAndNutritionLogScreen
import com.simats.enhancedcyclecareai.ui.timeline.HealthTimelineScreen
import com.simats.enhancedcyclecareai.ui.symptomnutrition.SymptomNutritionAnalysisScreen
import com.simats.enhancedcyclecareai.ui.progress.ProgressDashboardScreen
import com.simats.enhancedcyclecareai.ui.report.HealthReportScreen

sealed interface NavDestination {
    data object Splash : NavDestination
    data object Welcome : NavDestination
    data object Register : NavDestination
    data object Login : NavDestination
    data object HealthProfile : NavDestination
    data object DietaryPreferences : NavDestination
    data object Home : NavDestination
    data object CycleTracking : NavDestination
    data object CycleDetails : NavDestination
    data object SymptomTracking : NavDestination
    data object LifestyleTracking : NavDestination
    data object DataSummary : NavDestination
    data object DataPreprocessing : NavDestination
    data object AiHealthAnalysis : NavDestination
    data object PatternDetection : NavDestination
    data object PcosRelatedPattern : NavDestination
    data object GeneralMenstrualPattern : NavDestination
    data object PersonalizedGuidance : NavDestination
    data object PersonalizedNutrition : NavDestination
    data object PersonalizedMeal : NavDestination
    data object FoodAlternatives : NavDestination
    data object MealDetails : NavDestination
    data object MealFeedback : NavDestination
    data object MealAndNutritionLog : NavDestination
    data object HealthTimeline : NavDestination
    data object SymptomNutritionAnalysis : NavDestination
    data object ProgressDashboard : NavDestination
    data object HealthReport : NavDestination
    data object ScreenDirectory : NavDestination
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

    val handleTab: (String) -> Unit = { tab ->
        currentDestination = when (tab) {
            "Home" -> NavDestination.Home
            "Track" -> NavDestination.CycleTracking
            "Insights" -> NavDestination.DataSummary
            "Nutrition" -> NavDestination.PersonalizedNutrition
            "Progress" -> NavDestination.ProgressDashboard
            else -> currentDestination
        }
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
                        currentDestination = NavDestination.DietaryPreferences
                    }
                )
            }
            NavDestination.DietaryPreferences -> {
                DietaryPreferencesScreen(
                    onNavigateBack = {
                        currentDestination = NavDestination.HealthProfile
                    },
                    onNavigateHome = {
                        currentDestination = NavDestination.Home
                    },
                    onNavigateTab = handleTab
                )
            }
            NavDestination.Home -> {
                HomeScreen(
                    onNavigateBack = {
                        currentDestination = NavDestination.HealthProfile
                    },
                    onNavigateToScreenDirectory = {
                        currentDestination = NavDestination.ScreenDirectory
                    },
                    onNavigateToCycleTracking = {
                        currentDestination = NavDestination.CycleTracking
                    },
                    onNavigateToSymptomTracking = {
                        currentDestination = NavDestination.SymptomTracking
                    },
                    onNavigateToLifestyleTracking = {
                        currentDestination = NavDestination.LifestyleTracking
                    },
                    onNavigateToNutrition = {
                        currentDestination = NavDestination.PersonalizedNutrition
                    },
                    onNavigateToInsights = {
                        currentDestination = NavDestination.DataSummary
                    }
                )
            }
            NavDestination.CycleTracking -> {
                CycleTrackingScreen(
                    onNavigateBack = {
                        currentDestination = NavDestination.Home
                    },
                    onNavigateTab = handleTab
                )
            }
            NavDestination.CycleDetails -> {
                CycleDetailsScreen(
                    onNavigateBack = {
                        currentDestination = NavDestination.CycleTracking
                    },
                    onNavigateTab = handleTab
                )
            }
            NavDestination.SymptomTracking -> {
                SymptomTrackingScreen(
                    onNavigateBack = {
                        currentDestination = NavDestination.Home
                    },
                    onNavigateTab = handleTab
                )
            }
            NavDestination.LifestyleTracking -> {
                LifestyleTrackingScreen(
                    onNavigateBack = {
                        currentDestination = NavDestination.Home
                    },
                    onNavigateTab = handleTab
                )
            }
            NavDestination.DataSummary -> {
                DataSummaryScreen(
                    onNavigateBack = {
                        currentDestination = NavDestination.Home
                    },
                    onNavigateTab = handleTab
                )
            }
            NavDestination.DataPreprocessing -> {
                DataPreprocessingScreen(
                    onNavigateBack = {
                        currentDestination = NavDestination.DataSummary
                    },
                    onNavigateTab = handleTab
                )
            }
            NavDestination.AiHealthAnalysis -> {
                AiHealthAnalysisScreen(
                    onNavigateBack = {
                        currentDestination = NavDestination.DataPreprocessing
                    },
                    onNavigateTab = handleTab
                )
            }
            NavDestination.PatternDetection -> {
                PatternDetectionScreen(
                    onNavigateBack = {
                        currentDestination = NavDestination.AiHealthAnalysis
                    },
                    onNavigateTab = handleTab,
                    onNavigateToPcos = {
                        currentDestination = NavDestination.PcosRelatedPattern
                    },
                    onNavigateToGeneralMenstrualPattern = {
                        currentDestination = NavDestination.GeneralMenstrualPattern
                    },
                    onNavigateToGuidance = {
                        currentDestination = NavDestination.PersonalizedGuidance
                    }
                )
            }
            NavDestination.PcosRelatedPattern -> {
                PcosRelatedPatternScreen(
                    onNavigateBack = {
                        currentDestination = NavDestination.PatternDetection
                    }
                )
            }
            NavDestination.GeneralMenstrualPattern -> {
                GeneralMenstrualPatternScreen(
                    onNavigateBack = {
                        currentDestination = NavDestination.PatternDetection
                    },
                    onNavigateToGuidance = {
                        currentDestination = NavDestination.PersonalizedGuidance
                    }
                )
            }
            NavDestination.PersonalizedGuidance -> {
                PersonalizedGuidanceScreen(
                    onNavigateBack = {
                        currentDestination = NavDestination.PatternDetection
                    },
                    onNavigateToCycleTracker = {
                        currentDestination = NavDestination.CycleTracking
                    },
                    onNavigateToSymptomTracker = {
                        currentDestination = NavDestination.SymptomTracking
                    },
                    onNavigateToLifestyleTracker = {
                        currentDestination = NavDestination.LifestyleTracking
                    },
                    onNavigateToNutrition = {
                        currentDestination = NavDestination.PersonalizedNutrition
                    }
                )
            }
            NavDestination.PersonalizedNutrition -> {
                PersonalizedNutritionScreen(
                    onNavigateBack = {
                        currentDestination = NavDestination.PersonalizedGuidance
                    },
                    onNavigateTab = handleTab,
                    onNavigateToMeals = {
                        currentDestination = NavDestination.PersonalizedMeal
                    },
                    onNavigateToAlternatives = {
                        currentDestination = NavDestination.FoodAlternatives
                    }
                )
            }
            NavDestination.PersonalizedMeal -> {
                PersonalizedMealScreen(
                    onNavigateBack = {
                        currentDestination = NavDestination.PersonalizedNutrition
                    },
                    onNavigateTab = handleTab,
                    onNavigateToMealDetails = {
                        currentDestination = NavDestination.MealDetails
                    },
                    onNavigateOpenLog = {
                        currentDestination = NavDestination.MealAndNutritionLog
                    }
                )
            }
            NavDestination.FoodAlternatives -> {
                FoodAlternativesScreen(
                    onNavigateBack = {
                        currentDestination = NavDestination.PersonalizedNutrition
                    },
                    onNavigateTab = handleTab
                )
            }
            NavDestination.MealDetails -> {
                MealDetailsScreen(
                    onNavigateBack = {
                        currentDestination = NavDestination.PersonalizedMeal
                    },
                    onNavigateReplaceMeal = {
                        currentDestination = NavDestination.FoodAlternatives
                    },
                    onNavigateTab = handleTab
                )
            }
            NavDestination.MealFeedback -> {
                MealFeedbackScreen(
                    onNavigateBack = {
                        currentDestination = NavDestination.PersonalizedMeal
                    },
                    onNavigateTab = handleTab
                )
            }
            NavDestination.MealAndNutritionLog -> {
                MealAndNutritionLogScreen(
                    onNavigateBack = {
                        currentDestination = NavDestination.PersonalizedMeal
                    },
                    onNavigateTab = handleTab
                )
            }
            NavDestination.HealthTimeline -> {
                HealthTimelineScreen(
                    onNavigateBack = {
                        currentDestination = NavDestination.CycleTracking
                    },
                    onNavigateTab = handleTab
                )
            }
            NavDestination.SymptomNutritionAnalysis -> {
                SymptomNutritionAnalysisScreen(
                    onNavigateBack = {
                        currentDestination = NavDestination.DataSummary
                    },
                    onNavigateTab = handleTab
                )
            }
            NavDestination.ProgressDashboard -> {
                ProgressDashboardScreen(
                    onNavigateBack = {
                        currentDestination = NavDestination.Home
                    },
                    onNavigateTab = handleTab,
                    onNavigateToHealthReport = {
                        currentDestination = NavDestination.HealthReport
                    }
                )
            }
            NavDestination.HealthReport -> {
                HealthReportScreen(
                    onNavigateBack = {
                        currentDestination = NavDestination.ProgressDashboard
                    },
                    onNavigateTab = handleTab
                )
            }
            NavDestination.ScreenDirectory -> {
                ScreenDirectoryScreen(
                    onNavigateBack = {
                        currentDestination = NavDestination.Home
                    },
                    onNavigateToScreen = { key ->
                        currentDestination = when (key) {
                            "Splash" -> NavDestination.Splash
                            "Welcome" -> NavDestination.Welcome
                            "Register" -> NavDestination.Register
                            "Login" -> NavDestination.Login
                            "HealthProfile" -> NavDestination.HealthProfile
                            "DietaryPreferences" -> NavDestination.DietaryPreferences
                            "Home" -> NavDestination.Home
                            "CycleTracking" -> NavDestination.CycleTracking
                            "CycleDetails" -> NavDestination.CycleDetails
                            "SymptomTracking" -> NavDestination.SymptomTracking
                            "LifestyleTracking" -> NavDestination.LifestyleTracking
                            "DataSummary" -> NavDestination.DataSummary
                            "DataPreprocessing" -> NavDestination.DataPreprocessing
                            "AiHealthAnalysis" -> NavDestination.AiHealthAnalysis
                            "PatternDetection" -> NavDestination.PatternDetection
                            "PcosRelatedPattern" -> NavDestination.PcosRelatedPattern
                            "GeneralMenstrualPattern" -> NavDestination.GeneralMenstrualPattern
                            "PersonalizedMeal" -> NavDestination.PersonalizedMeal
                            "FoodAlternatives" -> NavDestination.FoodAlternatives
                            "MealDetails" -> NavDestination.MealDetails
                            "MealFeedback" -> NavDestination.MealFeedback
                            "MealAndNutritionLog" -> NavDestination.MealAndNutritionLog
                            "HealthTimeline" -> NavDestination.HealthTimeline
                            "SymptomNutritionAnalysis" -> NavDestination.SymptomNutritionAnalysis
                            "ProgressDashboard" -> NavDestination.ProgressDashboard
                            "HealthReport" -> NavDestination.HealthReport
                            else -> NavDestination.Home
                        }
                    },
                    onNavigateTab = handleTab
                )
            }
        }
    }
}
