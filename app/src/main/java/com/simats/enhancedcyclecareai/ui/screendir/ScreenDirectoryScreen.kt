package com.simats.enhancedcyclecareai.ui.screendir

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
fun ScreenDirectoryScreen(
    modifier: Modifier = Modifier,
    viewModel: ScreenDirectoryViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    onNavigateToScreen: (String) -> Unit = {},
    onNavigateTab: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedTab by viewModel.selectedTab.collectAsState()
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is ScreenDirectoryUiEvent.NavigateBack -> onNavigateBack()
                is ScreenDirectoryUiEvent.ShowToast -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                is ScreenDirectoryUiEvent.NavigateToScreen -> onNavigateToScreen(event.destinationKey)
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

                // 1. Header
                ScreenDirectoryHeader(
                    subtitle = uiState.subtitle,
                    title = uiState.title,
                    onBackClick = { viewModel.onBackClicked() }
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

                // 3. Category Filter Chips
                val categories = listOf("All", "Onboarding & Auth", "Core Tracking", "Insights & AI", "Personalized Care")
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    categories.forEach { cat ->
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
                            Text(
                                text = cat,
                                fontSize = 13.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Color.White else TitleDark
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // 4. Screens List / Grid
                val filteredScreens = if (uiState.selectedCategory == "All") {
                    uiState.screens
                } else {
                    uiState.screens.filter { it.category == uiState.selectedCategory }
                }

                filteredScreens.forEach { screen ->
                    ScreenCard(
                        screen = screen,
                        onClick = { viewModel.onScreenSelected(screen.destinationKey, screen.title) }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        // 5. Bottom Navigation Bar
        ScreenDirectoryBottomNavigationBar(
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
private fun ScreenDirectoryHeader(
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
private fun ScreenCard(
    screen: ScreenItem,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(1.dp, FieldBorder, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(BadgeBackground),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = screen.iconRes),
                    contentDescription = null,
                    tint = PrimaryPurple,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = screen.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TitleDark
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(BadgeBackground)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = screen.category,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = PrimaryPurple
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = screen.description,
                    fontSize = 13.sp,
                    color = SubtitleMuted
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = "Open",
                tint = SubtitleMuted,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
private fun ScreenDirectoryBottomNavigationBar(
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
            DirNavItem(
                label = "Home",
                iconRes = R.drawable.ic_home_tab,
                isSelected = selectedTab == "Home",
                onClick = { onTabSelected("Home") }
            )
            DirNavItem(
                label = "Track",
                iconRes = R.drawable.ic_calendar,
                isSelected = selectedTab == "Track",
                onClick = { onTabSelected("Track") }
            )
            DirNavItem(
                label = "Insights",
                iconRes = R.drawable.ic_sparkles,
                isSelected = selectedTab == "Insights",
                onClick = { onTabSelected("Insights") }
            )
            DirNavItem(
                label = "Nutrition",
                iconRes = R.drawable.ic_leaf,
                isSelected = selectedTab == "Nutrition",
                onClick = { onTabSelected("Nutrition") }
            )
            DirNavItem(
                label = "Profile",
                iconRes = R.drawable.ic_person,
                isSelected = selectedTab == "Profile",
                onClick = { onTabSelected("Profile") }
            )
        }
    }
}

@Composable
private fun DirNavItem(
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
