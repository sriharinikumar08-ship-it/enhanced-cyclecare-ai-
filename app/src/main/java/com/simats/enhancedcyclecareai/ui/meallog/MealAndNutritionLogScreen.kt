package com.simats.enhancedcyclecareai.ui.meallog

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.simats.enhancedcyclecareai.R
import com.simats.enhancedcyclecareai.ui.theme.BadgeBackground
import com.simats.enhancedcyclecareai.ui.theme.FieldBorder
import com.simats.enhancedcyclecareai.ui.theme.PrimaryPurple
import com.simats.enhancedcyclecareai.ui.theme.SplashBackground
import com.simats.enhancedcyclecareai.ui.theme.SubtitleMuted
import com.simats.enhancedcyclecareai.ui.theme.TitleDark

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MealAndNutritionLogScreen(
    modifier: Modifier = Modifier,
    viewModel: MealAndNutritionLogViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    onNavigateTab: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedTab by viewModel.selectedTab.collectAsState()
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is MealAndNutritionLogUiEvent.NavigateBack -> onNavigateBack()
                is MealAndNutritionLogUiEvent.LogMeal -> {}
                is MealAndNutritionLogUiEvent.ShowToast -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SplashBackground)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 68.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(12.dp))

                // 1. Header Bar
                LogHeader(
                    subtitle = uiState.subtitle,
                    title = uiState.title,
                    onBackClick = { viewModel.onBackClicked() }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 2. Date Range Header & Calendar View button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = uiState.dateRangeHeader,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TitleDark
                    )
                    Text(
                        text = "Calendar view",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = PrimaryPurple,
                        modifier = Modifier.clickable {
                            Toast.makeText(context, "Opening calendar view...", Toast.LENGTH_SHORT).show()
                        }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // 3. Week Day Selector Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    uiState.days.forEach { day ->
                        DaySelectorItem(
                            day = day,
                            onClick = { viewModel.onDaySelected(day.dayNumber) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 4. Summary Cards Row (2 cards)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Card 1
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFFEAF3ED))
                            .border(1.dp, Color(0xFFD4E8DC), RoundedCornerShape(16.dp))
                            .padding(14.dp)
                    ) {
                        Column {
                            Text(
                                text = uiState.loggedSlotsValue,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = TitleDark
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = uiState.loggedSlotsLabel,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = TitleDark
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = uiState.loggedSlotsSub,
                                fontSize = 11.sp,
                                color = SubtitleMuted
                            )
                        }
                    }

                    // Card 2
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFFF3EEF9))
                            .border(1.dp, Color(0xFFE8DEF2), RoundedCornerShape(16.dp))
                            .padding(14.dp)
                    ) {
                        Column {
                            Text(
                                text = uiState.followedValue,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryPurple
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = uiState.followedLabel,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = TitleDark
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = uiState.followedSub,
                                fontSize = 11.sp,
                                color = SubtitleMuted
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 5. Category Filter Chips Row
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    uiState.categories.forEach { cat ->
                        val isSelected = cat == uiState.selectedCategory
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(if (isSelected) PrimaryPurple else Color.White)
                                .border(
                                    1.dp,
                                    if (isSelected) PrimaryPurple else FieldBorder,
                                    RoundedCornerShape(20.dp)
                                )
                                .clickable { viewModel.onCategorySelected(cat) }
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (isSelected && cat == "All") {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_check),
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                }
                                Text(
                                    text = cat,
                                    fontSize = 13.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) Color.White else TitleDark
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // 6. Log Sections
                uiState.sections.forEach { section ->
                    LogSectionCard(
                        section = section,
                        onAddMeal = { viewModel.onLogMealClicked() }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // 7. Nutrition Activity Card
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color(0xFFEAF3ED))
                        .border(1.dp, Color(0xFFD4E8DC), RoundedCornerShape(18.dp))
                        .padding(16.dp)
                ) {
                    Column {
                        Text(
                            text = uiState.nutritionActivityTitle,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TitleDark
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = uiState.nutritionActivityText,
                            fontSize = 13.sp,
                            color = TitleDark,
                            lineHeight = 19.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 8. Footer Disclaimer
                Text(
                    text = uiState.footerDisclaimer,
                    fontSize = 11.sp,
                    color = SubtitleMuted,
                    lineHeight = 15.sp,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 9. Log a Meal Button
                Button(
                    onClick = { viewModel.onLogMealClicked() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryPurple,
                        contentColor = Color.White
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_plus),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Log a Meal",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        // 10. Bottom Navigation Bar
        LogBottomNavigationBar(
            selectedTab = selectedTab,
            onTabSelected = { tab ->
                viewModel.onTabSelected(tab)
                onNavigateTab(tab)
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun LogHeader(
    subtitle: String,
    title: String,
    onBackClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, FieldBorder, RoundedCornerShape(12.dp))
                .clickable { onBackClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "Back",
                tint = TitleDark,
                modifier = Modifier.size(20.dp)
            )
        }

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp)
        ) {
            Text(
                text = subtitle,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryPurple,
                letterSpacing = 1.sp
            )
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TitleDark
            )
        }

        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, FieldBorder, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_calendar),
                contentDescription = "Calendar",
                tint = SubtitleMuted,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun DaySelectorItem(
    day: DayItem,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .background(if (day.isSelected) PrimaryPurple else Color.White)
            .border(
                1.dp,
                if (day.isSelected) PrimaryPurple else FieldBorder,
                RoundedCornerShape(14.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ) {
        Text(
            text = day.dayLetter,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = if (day.isSelected) Color.White.copy(alpha = 0.8f) else SubtitleMuted
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "${day.dayNumber}",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = if (day.isSelected) Color.White else TitleDark
        )
    }
}

@Composable
private fun LogSectionCard(
    section: LogSection,
    onAddMeal: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Color.White)
            .border(1.dp, FieldBorder, RoundedCornerShape(18.dp))
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = section.dateHeader,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TitleDark
                )
                if (section.dateHeader.contains("Today")) {
                    Text(
                        text = "Add meal",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = PrimaryPurple,
                        modifier = Modifier.clickable { onAddMeal() }
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            section.meals.forEachIndexed { index, meal ->
                if (index > 0) {
                    Spacer(modifier = Modifier.height(10.dp))
                    HorizontalDivider(color = FieldBorder, thickness = 0.5.dp)
                    Spacer(modifier = Modifier.height(10.dp))
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = meal.categoryTime,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = SubtitleMuted
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = meal.title,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = TitleDark
                        )
                        if (!meal.subtitle.isNullOrEmpty()) {
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = meal.subtitle,
                                fontSize = 12.sp,
                                color = SubtitleMuted
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    val tagBg = when (meal.statusTag) {
                        "Followed" -> Color(0xFFEAF3ED)
                        "Skipped" -> Color(0xFFFEF3C7)
                        else -> Color(0xFFF3EEF9)
                    }
                    val tagColor = when (meal.statusTag) {
                        "Followed" -> Color(0xFF2E7D32)
                        "Skipped" -> Color(0xFFB45309)
                        else -> PrimaryPurple
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(tagBg)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = meal.statusTag,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = tagColor
                        )
                    }
                }
            }

            if (!section.footerNote.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = section.footerNote,
                    fontSize = 12.sp,
                    color = SubtitleMuted
                )
            }
        }
    }
}

@Composable
private fun LogBottomNavigationBar(
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(Color.White)
            .border(1.dp, FieldBorder, RectangleShape)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LogNavItem(
                label = "Home",
                iconRes = R.drawable.ic_home_tab,
                isSelected = selectedTab == "Home",
                onClick = { onTabSelected("Home") }
            )
            LogNavItem(
                label = "Track",
                iconRes = R.drawable.ic_calendar,
                isSelected = selectedTab == "Track",
                onClick = { onTabSelected("Track") }
            )
            LogNavItem(
                label = "Insights",
                iconRes = R.drawable.ic_sparkles,
                isSelected = selectedTab == "Insights",
                onClick = { onTabSelected("Insights") }
            )
            LogNavItem(
                label = "Nutrition",
                iconRes = R.drawable.ic_leaf,
                isSelected = selectedTab == "Nutrition",
                onClick = { onTabSelected("Nutrition") }
            )
            LogNavItem(
                label = "Progress",
                iconRes = R.drawable.ic_progress_tab,
                isSelected = selectedTab == "Progress",
                onClick = { onTabSelected("Progress") }
            )
        }
    }
}

@Composable
private fun LogNavItem(
    label: String,
    iconRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            tint = if (isSelected) PrimaryPurple else SubtitleMuted,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) PrimaryPurple else SubtitleMuted
        )
    }
}
