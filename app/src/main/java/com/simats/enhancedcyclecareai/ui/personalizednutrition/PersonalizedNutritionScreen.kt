package com.simats.enhancedcyclecareai.ui.personalizednutrition

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
fun PersonalizedNutritionScreen(
    modifier: Modifier = Modifier,
    viewModel: PersonalizedNutritionViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    onNavigateTab: (String) -> Unit = {},
    onNavigateToMeals: () -> Unit = {},
    onNavigateToAlternatives: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedTab by viewModel.selectedTab.collectAsState()
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is PersonalizedNutritionUiEvent.NavigateBack -> onNavigateBack()
                is PersonalizedNutritionUiEvent.ShowToast -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                is PersonalizedNutritionUiEvent.SeeRecommendedMeals -> onNavigateToMeals()
                is PersonalizedNutritionUiEvent.BrowseAlternatives -> onNavigateToAlternatives()
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
                NutritionHeader(
                    subtitle = uiState.subtitle,
                    title = uiState.title,
                    onBackClick = {
                        viewModel.onBackClicked()
                        onNavigateBack()
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                // 2. Intro Text
                Text(
                    text = uiState.introText,
                    fontSize = 14.sp,
                    color = SubtitleMuted,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 3. Hero Nourish Card (Soft Teal background container)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFFE6F4F1))
                        .border(1.dp, Color(0xFFCEE8E4), RoundedCornerShape(20.dp))
                        .padding(20.dp)
                ) {
                    Column {
                        // Illustration mockup representation
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(110.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(90.dp)
                                    .clip(CircleShape)
                                    .background(Color.White),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_bowl),
                                    contentDescription = null,
                                    tint = PrimaryPurple,
                                    modifier = Modifier.size(42.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = uiState.heroTitle,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = TitleDark
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = uiState.heroBody,
                            fontSize = 13.sp,
                            color = TitleDark,
                            lineHeight = 19.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            uiState.filterTags.forEach { tag ->
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(Color.White)
                                        .border(1.dp, Color(0xFFCEE8E4), CircleShape)
                                        .padding(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = tag,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color(0xFF0D9488)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 4. "Why This Is Recommended" Card
                WhyRecommendedCard()

                Spacer(modifier = Modifier.height(16.dp))

                // 5. "Food groups to include" Card
                FoodGroupsCard()

                Spacer(modifier = Modifier.height(16.dp))

                // 6. "Make it work for you" Card
                MakeItWorkCard(
                    onBrowseAlternatives = {
                        viewModel.onBrowseAlternativesClicked()
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 7. Warning Banner Card
                NutritionWarningCard(
                    warningText = uiState.warningText
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 8. See Recommended Meals Button
                Button(
                    onClick = { viewModel.onSeeRecommendedMealsClicked() },
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
                            painter = painterResource(id = R.drawable.ic_bowl),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "See Recommended Meals",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        // 9. Bottom Navigation Bar
        NutritionBottomNavigationBar(
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
private fun NutritionHeader(
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
                painter = painterResource(id = R.drawable.ic_more),
                contentDescription = "Options",
                tint = SubtitleMuted,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun WhyRecommendedCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Color.White)
            .border(1.dp, FieldBorder, RoundedCornerShape(18.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = "Why This Is Recommended",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TitleDark
            )
            Spacer(modifier = Modifier.height(14.dp))

            NutritionItemRow(
                iconRes = R.drawable.ic_home_tab,
                title = "Fits your routine",
                subtitle = "Quick, home-style Indian meals for busy university days."
            )

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = FieldBorder, thickness = 0.5.dp)
            Spacer(modifier = Modifier.height(12.dp))

            NutritionItemRow(
                iconRes = R.drawable.ic_clipboard_info,
                title = "Supports general nourishment",
                subtitle = "Your fatigue and cycle entries prompted a balanced-eating reminder, not a treatment recommendation."
            )

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = FieldBorder, thickness = 0.5.dp)
            Spacer(modifier = Modifier.height(12.dp))

            NutritionItemRow(
                iconRes = R.drawable.ic_sparkles,
                title = "Matches your preferences",
                subtitle = "Vegetarian · no eggs · no mushrooms"
            )
        }
    }
}

@Composable
private fun FoodGroupsCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Color.White)
            .border(1.dp, FieldBorder, RoundedCornerShape(18.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = "Food groups to include",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TitleDark
            )
            Spacer(modifier = Modifier.height(14.dp))

            NutritionItemRow(
                iconRes = R.drawable.ic_leaf,
                title = "Protein & iron-containing foods",
                subtitle = "Lentils, chickpeas, tofu - add vitamin C-rich produce."
            )

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = FieldBorder, thickness = 0.5.dp)
            Spacer(modifier = Modifier.height(12.dp))

            NutritionItemRow(
                iconRes = R.drawable.ic_bowl,
                title = "Whole grains & fiber",
                subtitle = "Oats, brown rice, and seasonal vegetables."
            )

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = FieldBorder, thickness = 0.5.dp)
            Spacer(modifier = Modifier.height(12.dp))

            NutritionItemRow(
                iconRes = R.drawable.ic_check,
                title = "Calcium-containing choices",
                subtitle = "Plain yogurt or a suitable fortified alternative."
            )

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = FieldBorder, thickness = 0.5.dp)
            Spacer(modifier = Modifier.height(12.dp))

            NutritionItemRow(
                iconRes = R.drawable.ic_clock,
                title = "Hydration through the day",
                subtitle = "Use your 2 L personal reminder goal as a check-in; needs vary."
            )
        }
    }
}

@Composable
private fun NutritionItemRow(
    iconRes: Int,
    title: String,
    subtitle: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Color(0xFFF3E8FF)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = PrimaryPurple,
                modifier = Modifier.size(18.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = TitleDark
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = SubtitleMuted
            )
        }
    }
}

@Composable
private fun MakeItWorkCard(
    onBrowseAlternatives: () -> Unit
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
            Text(
                text = "Make it work for you",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TitleDark
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Swap yogurt for fortified soy yogurt, or lentils for chickpeas. Review ingredients and allergy information for every alternative.",
                fontSize = 13.sp,
                color = TitleDark,
                lineHeight = 19.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.clickable { onBrowseAlternatives() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Browse Food Alternatives →",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryPurple
                )
            }
        }
    }
}

@Composable
private fun NutritionWarningCard(
    warningText: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFFEF3C7))
            .border(1.dp, Color(0xFFFDE68A), RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_warning),
                contentDescription = null,
                tint = Color(0xFFB45309),
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = warningText,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF92400E),
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
private fun NutritionBottomNavigationBar(
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
            NutritionNavItem(
                label = "Home",
                iconRes = R.drawable.ic_home_tab,
                isSelected = selectedTab == "Home",
                onClick = { onTabSelected("Home") }
            )
            NutritionNavItem(
                label = "Track",
                iconRes = R.drawable.ic_calendar,
                isSelected = selectedTab == "Track",
                onClick = { onTabSelected("Track") }
            )
            NutritionNavItem(
                label = "Insights",
                iconRes = R.drawable.ic_sparkles,
                isSelected = selectedTab == "Insights",
                onClick = { onTabSelected("Insights") }
            )
            NutritionNavItem(
                label = "Nutrition",
                iconRes = R.drawable.ic_leaf,
                isSelected = selectedTab == "Nutrition",
                onClick = { onTabSelected("Nutrition") }
            )
            NutritionNavItem(
                label = "Progress",
                iconRes = R.drawable.ic_progress_tab,
                isSelected = selectedTab == "Progress",
                onClick = { onTabSelected("Progress") }
            )
        }
    }
}

@Composable
private fun NutritionNavItem(
    label: String,
    iconRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val tint = if (isSelected) PrimaryPurple else SubtitleMuted
    val bg = if (isSelected) BadgeBackground else Color.Transparent

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(bg)
            .clickable { onClick() }
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            tint = tint,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = tint
        )
    }
}
