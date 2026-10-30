package com.simats.enhancedcyclecareai.ui.screendir

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simats.enhancedcyclecareai.R
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface ScreenDirectoryUiEvent {
    data class NavigateToScreen(val destinationKey: String) : ScreenDirectoryUiEvent
    data object NavigateBack : ScreenDirectoryUiEvent
    data class ShowToast(val message: String) : ScreenDirectoryUiEvent
}

data class ScreenItem(
    val id: String,
    val title: String,
    val description: String,
    val category: String,
    val iconRes: Int,
    val destinationKey: String
)

data class ScreenDirectoryUiState(
    val subtitle: String = "APP DIRECTORY",
    val title: String = "All App Screens (30)",
    val introText: String = "Explore and test all 30 screens built across onboarding, tracking, insights, AI patterns, and personalized care.",
    val screens: List<ScreenItem> = listOf(
        // Onboarding & Auth
        ScreenItem("splash", "Splash Screen", "Initial app loading animation", "Onboarding & Auth", R.drawable.ic_sparkles, "Splash"),
        ScreenItem("welcome", "Welcome Screen", "Introduction & get started actions", "Onboarding & Auth", R.drawable.ic_person, "Welcome"),
        ScreenItem("register", "Register Screen", "User account creation", "Onboarding & Auth", R.drawable.ic_mail, "Register"),
        ScreenItem("login", "Login Screen", "User sign in flow", "Onboarding & Auth", R.drawable.ic_eye, "Login"),
        ScreenItem("health_profile", "Health Profile", "User health background setup", "Onboarding & Auth", R.drawable.ic_doctor, "HealthProfile"),
        ScreenItem("dietary_prefs", "Dietary Preferences", "Food and diet choices", "Onboarding & Auth", R.drawable.ic_leaf, "DietaryPreferences"),

        // Core Dashboard & Tracking
        ScreenItem("home", "Home Dashboard", "Main overview, cycle day, and quick actions", "Core Tracking", R.drawable.ic_home_tab, "Home"),
        ScreenItem("cycle_tracking", "Cycle Tracking", "Calendar and period logging", "Core Tracking", R.drawable.ic_calendar, "CycleTracking"),
        ScreenItem("cycle_details", "Cycle Details", "Detailed cycle flow metrics", "Core Tracking", R.drawable.ic_droplet, "CycleDetails"),
        ScreenItem("symptom_tracking", "Symptom Tracking", "Log cramps, mood, fatigue, headache", "Core Tracking", R.drawable.ic_symptoms, "SymptomTracking"),
        ScreenItem("lifestyle_tracking", "Lifestyle Tracking", "Sleep, water, exercise tracking", "Core Tracking", R.drawable.ic_lifestyle, "LifestyleTracking"),
        ScreenItem("health_timeline", "Health Timeline", "Unified cycle, symptom, lifestyle, and meal history", "Core Tracking", R.drawable.ic_timeline, "HealthTimeline"),
        ScreenItem("progress_dashboard", "Progress Dashboard", "Weekly check-ins, meal adherence, and checklist", "Core Tracking", R.drawable.ic_progress_tab, "ProgressDashboard"),

        // Insights & AI Analysis
        ScreenItem("data_summary", "Data Summary", "Aggregated health metrics", "Insights & AI", R.drawable.ic_database, "DataSummary"),
        ScreenItem("data_preprocessing", "Data Preprocessing", "AI cleaning & data status", "Insights & AI", R.drawable.ic_refresh, "DataPreprocessing"),
        ScreenItem("ai_health_analysis", "AI Health Analysis", "Comprehensive health assessment", "Insights & AI", R.drawable.ic_sparkles, "AiHealthAnalysis"),
        ScreenItem("pattern_detection", "Pattern Detection", "AI pattern discovery hub", "Insights & AI", R.drawable.ic_timeline, "PatternDetection"),
        ScreenItem("pcos_pattern", "PCOS Related Pattern", "Detailed PCOS pattern analysis", "Insights & AI", R.drawable.ic_clock_alert, "PcosRelatedPattern"),
        ScreenItem("menstrual_pattern", "General Menstrual Pattern", "Menstrual regularity and rhythm", "Insights & AI", R.drawable.ic_calendar, "GeneralMenstrualPattern"),
        ScreenItem("symptom_nutrition_analysis", "Symptom & Nutrition Analytics", "Correlation and patterns between symptoms and meals", "Insights & AI", R.drawable.ic_symptoms, "SymptomNutritionAnalysis"),

        // Personalized Guidance & Nutrition
        ScreenItem("personalized_guidance", "Personalized Guidance", "Tailored care recommendations", "Personalized Care", R.drawable.ic_doctor, "PersonalizedGuidance"),
        ScreenItem("personalized_nutrition", "Personalized Nutrition", "Dietary guidance and groups", "Personalized Care", R.drawable.ic_leaf, "PersonalizedNutrition"),
        ScreenItem("personalized_meal", "Personalized Meal", "Picked meals for your day", "Personalized Care", R.drawable.ic_bowl, "PersonalizedMeal"),
        ScreenItem("food_alternatives", "Food Alternatives", "Ingredient swaps and allergy safety", "Personalized Care", R.drawable.ic_check, "FoodAlternatives"),
        ScreenItem("meal_details", "Meal Details", "Recipe ingredients, preparation, and substitutions", "Personalized Care", R.drawable.ic_bowl, "MealDetails"),
        ScreenItem("meal_feedback", "Meal Feedback", "Review and log your meal choices", "Personalized Care", R.drawable.ic_clock, "MealFeedback"),
        ScreenItem("meal_and_nutrition_log", "Meal & Nutrition Log", "History, logs, and nutrition activity", "Personalized Care", R.drawable.ic_database, "MealAndNutritionLog"),
        ScreenItem("health_report", "Health Report", "Comprehensive patient health export and summary", "Personalized Care", R.drawable.ic_clipboard_info, "HealthReport")
    ),
    val selectedCategory: String = "All"
)

class ScreenDirectoryViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ScreenDirectoryUiState())
    val uiState: StateFlow<ScreenDirectoryUiState> = _uiState.asStateFlow()

    private val _selectedTab = MutableStateFlow("Home")
    val selectedTab: StateFlow<String> = _selectedTab.asStateFlow()

    private val _uiEvent = MutableSharedFlow<ScreenDirectoryUiEvent>()
    val uiEvent: SharedFlow<ScreenDirectoryUiEvent> = _uiEvent.asSharedFlow()

    fun onBackClicked() {
        viewModelScope.launch {
            _uiEvent.emit(ScreenDirectoryUiEvent.NavigateBack)
        }
    }

    fun onCategorySelected(category: String) {
        _uiState.value = _uiState.value.copy(selectedCategory = category)
    }

    fun onScreenSelected(destinationKey: String, screenTitle: String) {
        viewModelScope.launch {
            _uiEvent.emit(ScreenDirectoryUiEvent.ShowToast("Opening $screenTitle..."))
            _uiEvent.emit(ScreenDirectoryUiEvent.NavigateToScreen(destinationKey))
        }
    }

    fun onTabSelected(tab: String) {
        _selectedTab.value = tab
    }
}
