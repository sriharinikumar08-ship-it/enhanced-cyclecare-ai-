package com.simats.enhancedcyclecareai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.simats.enhancedcyclecareai.ui.navigation.AppNavigation
import com.simats.enhancedcyclecareai.ui.theme.EnhancedCyclecareAiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EnhancedCyclecareAiTheme {
                AppNavigation()
            }
        }
    }
}
