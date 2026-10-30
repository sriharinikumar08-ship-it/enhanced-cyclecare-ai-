package com.simats.enhancedcyclecareai.ui.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.simats.enhancedcyclecareai.R
import com.simats.enhancedcyclecareai.ui.theme.BadgeBackground
import com.simats.enhancedcyclecareai.ui.theme.FieldBorder
import com.simats.enhancedcyclecareai.ui.theme.MintCardBg
import com.simats.enhancedcyclecareai.ui.theme.PrimaryPurple
import com.simats.enhancedcyclecareai.ui.theme.PrivacyCardBg
import com.simats.enhancedcyclecareai.ui.theme.SoftGreenBg
import com.simats.enhancedcyclecareai.ui.theme.SoftPinkBg
import com.simats.enhancedcyclecareai.ui.theme.SoftPurpleBg
import com.simats.enhancedcyclecareai.ui.theme.SplashBackground
import com.simats.enhancedcyclecareai.ui.theme.SubtitleMuted
import com.simats.enhancedcyclecareai.ui.theme.TealAccent
import com.simats.enhancedcyclecareai.ui.theme.TitleDark

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    onNavigateToScreenDirectory: () -> Unit = {},
    onNavigateToCycleTracking: () -> Unit = {},
    onNavigateToSymptomTracking: () -> Unit = {},
    onNavigateToLifestyleTracking: () -> Unit = {},
    onNavigateToNutrition: () -> Unit = {},
    onNavigateToInsights: () -> Unit = {}
) {
    val userName by viewModel.userName.collectAsState()
    val cycleDay by viewModel.cycleDay.collectAsState()
    val lastPeriodText by viewModel.lastPeriodText.collectAsState()
    val nextPeriodText by viewModel.nextPeriodText.collectAsState()
    val selectedTab by viewModel.selectedTab.collectAsState()
    val recentRecords by viewModel.recentRecords.collectAsState()

    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is HomeUiEvent.NavigateBack -> onNavigateBack()
                is HomeUiEvent.NavigateToTab -> {
                    when (event.tabName) {
                        "Track" -> onNavigateToCycleTracking()
                        "Insights" -> onNavigateToInsights()
                        "Nutrition" -> onNavigateToNutrition()
                    }
                }
                is HomeUiEvent.NavigateToFeature -> {
                    when (event.featureName) {
                        "Track Cycle" -> onNavigateToCycleTracking()
                        "Symptoms" -> onNavigateToSymptomTracking()
                        "Lifestyle" -> onNavigateToLifestyleTracking()
                        "Nutrition" -> onNavigateToNutrition()
                        "AI Insights" -> onNavigateToInsights()
                        "Timeline" -> onNavigateToInsights()
                    }
                }
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
                    .verticalScroll(scrollState)
            ) {
                Spacer(modifier = Modifier.height(12.dp))

                // 1. Top Bar Header
                HomeHeader(
                    userName = userName,
                    onBackClick = {
                        viewModel.onBackClicked()
                        onNavigateBack()
                    }
                )

                Spacer(modifier = Modifier.height(4.dp))

                // 2. Greeting Subtitle
                Text(
                    text = "A moment to check in with yourself.",
                    fontSize = 14.sp,
                    color = SubtitleMuted
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 2.5 All Screens Directory Banner
                AllScreensDirectoryBanner(
                    onClick = { onNavigateToScreenDirectory() }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 3. Cycle Overview Card
                CycleOverviewCard(
                    cycleDay = cycleDay,
                    lastPeriodText = lastPeriodText,
                    nextPeriodText = nextPeriodText
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 4. Your Daily Check-in Grid
                DailyCheckInSection(
                    onFeatureClick = viewModel::onFeatureClicked
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 5. Recently Recorded Section
                RecentlyRecordedSection(records = recentRecords)

                Spacer(modifier = Modifier.height(20.dp))

                // 6. AI Pattern Card
                AiPatternCard(
                    onExploreClick = { viewModel.onFeatureClicked("AI Insights") }
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 7. A Nourishing Idea Section
                NourishingIdeaSection()

                Spacer(modifier = Modifier.height(20.dp))

                // 8. Bottom Disclaimer Card
                HomeDisclaimerCard()

                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        // 9. Bottom Navigation Bar
        BottomNavigationBar(
            selectedTab = selectedTab,
            onTabSelected = viewModel::onTabSelected,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun HomeHeader(
    userName: String,
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
                text = "THURSDAY, 22 OCTOBER",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryPurple,
                letterSpacing = 1.sp
            )
            Text(
                text = "Hello, $userName",
                fontSize = 20.sp,
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
                painter = painterResource(id = R.drawable.ic_bell),
                contentDescription = "Notifications",
                tint = SubtitleMuted,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun CycleOverviewCard(
    cycleDay: Int,
    lastPeriodText: String,
    nextPeriodText: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(PrivacyCardBg)
            .padding(18.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Ring Progress Gauge
                Box(
                    modifier = Modifier.size(85.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawArc(
                            color = Color(0xFFE8DEF2),
                            startAngle = 0f,
                            sweepAngle = 360f,
                            useCenter = false,
                            style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
                        )
                        drawArc(
                            color = PrimaryPurple,
                            startAngle = -90f,
                            sweepAngle = 220f,
                            useCenter = false,
                            style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "$cycleDay",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryPurple
                        )
                        Text(
                            text = "CYCLE DAY",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryPurple
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = "Between periods",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = PrimaryPurple
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Your rhythm, at a glance",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TitleDark
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Last period: $lastPeriodText",
                        fontSize = 12.sp,
                        color = SubtitleMuted
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Estimated next period",
                    fontSize = 12.sp,
                    color = SubtitleMuted
                )
                Text(
                    text = nextPeriodText,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryPurple
                )
            }
        }
    }
}

@Composable
private fun DailyCheckInSection(
    onFeatureClick: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Your daily check-in",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TitleDark
            )
            Text(
                text = "View all",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryPurple,
                modifier = Modifier.clickable { onFeatureClick("View All") }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 2x3 Grid
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CheckInCard(
                    iconRes = R.drawable.ic_calendar,
                    title = "Track Cycle",
                    modifier = Modifier.weight(1f),
                    onClick = { onFeatureClick("Track Cycle") }
                )
                CheckInCard(
                    iconRes = R.drawable.ic_symptoms,
                    title = "Symptoms",
                    modifier = Modifier.weight(1f),
                    onClick = { onFeatureClick("Symptoms") }
                )
                CheckInCard(
                    iconRes = R.drawable.ic_lifestyle,
                    title = "Lifestyle",
                    modifier = Modifier.weight(1f),
                    onClick = { onFeatureClick("Lifestyle") }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CheckInCard(
                    iconRes = R.drawable.ic_leaf,
                    title = "Nutrition",
                    modifier = Modifier.weight(1f),
                    onClick = { onFeatureClick("Nutrition") }
                )
                CheckInCard(
                    iconRes = R.drawable.ic_sparkles,
                    title = "AI Insights",
                    modifier = Modifier.weight(1f),
                    onClick = { onFeatureClick("AI Insights") }
                )
                CheckInCard(
                    iconRes = R.drawable.ic_timeline,
                    title = "Timeline",
                    modifier = Modifier.weight(1f),
                    onClick = { onFeatureClick("Timeline") }
                )
            }
        }
    }
}

@Composable
private fun CheckInCard(
    iconRes: Int,
    title: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(82.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(1.dp, FieldBorder, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = title,
                tint = PrimaryPurple,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = TitleDark
            )
        }
    }
}

@Composable
private fun RecentlyRecordedSection(
    records: List<RecentRecord>
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
                text = "Recently recorded",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TitleDark
            )
            Text(
                text = "Your latest entries, not live measurements",
                fontSize = 12.sp,
                color = SubtitleMuted
            )

            Spacer(modifier = Modifier.height(14.dp))

            records.forEachIndexed { index, record ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(if (record.isPinkIcon) SoftPinkBg else SoftPurpleBg),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(
                                    id = if (record.isPinkIcon) R.drawable.ic_symptoms else R.drawable.ic_lifestyle
                                ),
                                contentDescription = null,
                                tint = PrimaryPurple,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = record.title,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TitleDark
                            )
                            Text(
                                text = record.subtitle,
                                fontSize = 12.sp,
                                color = SubtitleMuted
                            )
                        }
                    }

                    Text(
                        text = record.actionText,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryPurple,
                        modifier = Modifier.clickable { }
                    )
                }

                if (index < records.size - 1) {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
private fun AiPatternCard(
    onExploreClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(MintCardBg)
            .padding(16.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_sparkles),
                    contentDescription = null,
                    tint = TealAccent,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "A pattern worth noticing",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TitleDark
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Your recorded cycles range from 28–30 days. More tracking can help clarify variation.",
                fontSize = 13.sp,
                color = TitleDark,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Explore your AI Insights →",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = TealAccent,
                modifier = Modifier.clickable { onExploreClick() }
            )
        }
    }
}

@Composable
private fun NourishingIdeaSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "A nourishing idea",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TitleDark
            )
            Text(
                text = "Meal planner",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryPurple,
                modifier = Modifier.clickable { }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(Color.White)
                .border(1.dp, FieldBorder, RoundedCornerShape(18.dp))
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(SoftGreenBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_bowl),
                        contentDescription = null,
                        tint = TealAccent,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Text(
                        text = "Lentil & spinach bowl",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TitleDark
                    )
                    Text(
                        text = "Vegetarian · Protein · Fiber",
                        fontSize = 12.sp,
                        color = SubtitleMuted
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "See today's recipe +",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryPurple,
                        modifier = Modifier.clickable { }
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeDisclaimerCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(PrivacyCardBg)
            .border(1.dp, Color(0xFFE8DEF2), RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_heart_privacy),
                contentDescription = null,
                tint = PrimaryPurple,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Period estimates may change as you add records. Listen to your body, not just the calendar.",
                fontSize = 11.sp,
                color = PrimaryPurple,
                lineHeight = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun BottomNavigationBar(
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(Color.White)
            .border(1.dp, FieldBorder, RectangleShape())
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavItem(
                label = "Home",
                iconRes = R.drawable.ic_home_tab,
                isSelected = selectedTab == "Home",
                onClick = { onTabSelected("Home") }
            )
            NavItem(
                label = "Track",
                iconRes = R.drawable.ic_calendar,
                isSelected = selectedTab == "Track",
                onClick = { onTabSelected("Track") }
            )
            NavItem(
                label = "Insights",
                iconRes = R.drawable.ic_sparkles,
                isSelected = selectedTab == "Insights",
                onClick = { onTabSelected("Insights") }
            )
            NavItem(
                label = "Nutrition",
                iconRes = R.drawable.ic_leaf,
                isSelected = selectedTab == "Nutrition",
                onClick = { onTabSelected("Nutrition") }
            )
            NavItem(
                label = "Progress",
                iconRes = R.drawable.ic_progress_tab,
                isSelected = selectedTab == "Progress",
                onClick = { onTabSelected("Progress") }
            )
        }
    }
}

@Composable
private fun NavItem(
    label: String,
    iconRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val tint = if (isSelected) PrimaryPurple else SubtitleMuted
    val bg = if (isSelected) BadgeBackground else Color.Transparent

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
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
            modifier = Modifier.size(18.dp)
        )
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = tint
        )
    }
}

private fun RectangleShape() = RoundedCornerShape(0.dp)

@Composable
private fun AllScreensDirectoryBanner(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(BadgeBackground)
            .border(1.dp, Color(0xFFD8C8EC), RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(PrimaryPurple),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_sparkles),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Browse All 21 App Screens",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryPurple
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Tap here to explore, test, and view every screen in the app instantly.",
                    fontSize = 12.sp,
                    color = SubtitleMuted,
                    lineHeight = 16.sp
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = null,
                tint = PrimaryPurple,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
