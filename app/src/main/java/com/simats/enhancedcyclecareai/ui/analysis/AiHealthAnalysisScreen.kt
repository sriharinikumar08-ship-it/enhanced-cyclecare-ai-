package com.simats.enhancedcyclecareai.ui.analysis

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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.simats.enhancedcyclecareai.R
import com.simats.enhancedcyclecareai.ui.theme.BadgeBackground
import com.simats.enhancedcyclecareai.ui.theme.FieldBorder
import com.simats.enhancedcyclecareai.ui.theme.PrimaryPurple
import com.simats.enhancedcyclecareai.ui.theme.PrivacyCardBg
import com.simats.enhancedcyclecareai.ui.theme.SoftGreenBg
import com.simats.enhancedcyclecareai.ui.theme.SoftPurpleBg
import com.simats.enhancedcyclecareai.ui.theme.SplashBackground
import com.simats.enhancedcyclecareai.ui.theme.SubtitleMuted
import com.simats.enhancedcyclecareai.ui.theme.TealAccent
import com.simats.enhancedcyclecareai.ui.theme.TitleDark

data class PatternAnalysisItem(
    val title: String,
    val description: String,
    val status: String,
    val iconRes: Int,
    val progress: Float,
    val isReviewed: Boolean,
    val isEvaluating: Boolean
)

@Composable
fun AiHealthAnalysisScreen(
    modifier: Modifier = Modifier,
    viewModel: AiHealthAnalysisViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    onNavigateTab: (String) -> Unit = {}
) {
    val analysisProgress by viewModel.analysisProgress.collectAsState()
    val selectedTab by viewModel.selectedTab.collectAsState()

    val scrollState = rememberScrollState()

    val patternItems = listOf(
        PatternAnalysisItem(
            title = "Cycle Pattern",
            description = "Comparing length and variation",
            status = "Reviewed",
            iconRes = R.drawable.ic_calendar,
            progress = 1.0f,
            isReviewed = true,
            isEvaluating = false
        ),
        PatternAnalysisItem(
            title = "Symptom Pattern",
            description = "Looking at timing and recurrence",
            status = "Reviewed",
            iconRes = R.drawable.ic_symptoms,
            progress = 1.0f,
            isReviewed = true,
            isEvaluating = false
        ),
        PatternAnalysisItem(
            title = "Lifestyle Pattern",
            description = "Considering sleep and activity logs",
            status = "Evaluating",
            iconRes = R.drawable.ic_lifestyle,
            progress = 0.6f,
            isReviewed = false,
            isEvaluating = true
        ),
        PatternAnalysisItem(
            title = "PCOS-Related Pattern Awareness",
            description = "Reviewing recorded indicators and limits",
            status = "Queued",
            iconRes = R.drawable.ic_sparkles,
            progress = 0.0f,
            isReviewed = false,
            isEvaluating = false
        )
    )

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
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(12.dp))

                // 1. Header Bar
                AnalysisHeader(
                    onBackClick = {
                        viewModel.onBackClicked()
                        onNavigateBack()
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 2. Circular Gauge & Hero Section
                AnalysisHeroSection(analysisProgress = analysisProgress)

                Spacer(modifier = Modifier.height(24.dp))

                // 3. Pattern Analysis Cards List
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    patternItems.forEach { item ->
                        PatternItemCard(item = item)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // 4. Disclaimer Notice Card
                AnalysisDisclaimerCard()

                Spacer(modifier = Modifier.height(16.dp))

                // 5. Footer Subtitle
                Text(
                    text = "Next: an explainable summary with contributing observations and practical next steps.",
                    fontSize = 12.sp,
                    color = SubtitleMuted,
                    textAlign = TextAlign.Center,
                    lineHeight = 17.sp,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        // 6. Bottom Navigation Bar
        AnalysisBottomNavigationBar(
            selectedTab = selectedTab,
            onTabSelected = onNavigateTab,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun AnalysisHeader(
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
                text = "AI INSIGHTS · STEP 2 OF 3",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryPurple,
                letterSpacing = 1.sp
            )
            Text(
                text = "AI health analysis",
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
private fun AnalysisHeroSection(
    analysisProgress: Int
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Ring Gauge with Sparkle Icon
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
                    sweepAngle = (360f * (analysisProgress / 100f)),
                    useCenter = false,
                    style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
                )
            }
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(SoftPurpleBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_sparkles),
                    contentDescription = null,
                    tint = PrimaryPurple,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Connecting your observations",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TitleDark,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Analyzing your recorded information...",
            fontSize = 14.sp,
            color = SubtitleMuted,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Analysis In Progress Pill
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(BadgeBackground)
                .padding(horizontal = 14.dp, vertical = 6.dp)
        ) {
            Text(
                text = "Analysis in progress · $analysisProgress%",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = PrimaryPurple
            )
        }
    }
}

@Composable
private fun PatternItemCard(
    item: PatternAnalysisItem
) {
    val statusBg = when {
        item.isReviewed -> SoftGreenBg
        item.isEvaluating -> BadgeBackground
        else -> PrivacyCardBg
    }

    val statusTextColor = when {
        item.isReviewed -> TealAccent
        item.isEvaluating -> PrimaryPurple
        else -> SubtitleMuted
    }

    val barColor = when {
        item.isReviewed -> TealAccent
        item.isEvaluating -> PrimaryPurple
        else -> Color(0xFFE8DEF2)
    }

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
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(SoftPurpleBg),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = item.iconRes),
                            contentDescription = null,
                            tint = PrimaryPurple,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = item.title,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TitleDark
                        )
                        Text(
                            text = item.description,
                            fontSize = 12.sp,
                            color = SubtitleMuted
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(statusBg)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = item.status,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = statusTextColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            LinearProgressIndicator(
                progress = { item.progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(CircleShape),
                color = barColor,
                trackColor = Color(0xFFE8DEF2)
            )
        }
    }
}

@Composable
private fun AnalysisDisclaimerCard() {
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
                painter = painterResource(id = R.drawable.ic_sparkles),
                contentDescription = null,
                tint = PrimaryPurple,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "AI looks for associations in your entries. It cannot confirm a condition or establish why a symptom happened.",
                fontSize = 11.sp,
                color = PrimaryPurple,
                lineHeight = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun AnalysisBottomNavigationBar(
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
            AnalysisNavItem(
                label = "Home",
                iconRes = R.drawable.ic_home_tab,
                isSelected = selectedTab == "Home",
                onClick = { onTabSelected("Home") }
            )
            AnalysisNavItem(
                label = "Track",
                iconRes = R.drawable.ic_calendar,
                isSelected = selectedTab == "Track",
                onClick = { onTabSelected("Track") }
            )
            AnalysisNavItem(
                label = "Insights",
                iconRes = R.drawable.ic_sparkles,
                isSelected = selectedTab == "Insights",
                onClick = { onTabSelected("Insights") }
            )
            AnalysisNavItem(
                label = "Nutrition",
                iconRes = R.drawable.ic_leaf,
                isSelected = selectedTab == "Nutrition",
                onClick = { onTabSelected("Nutrition") }
            )
            AnalysisNavItem(
                label = "Progress",
                iconRes = R.drawable.ic_progress_tab,
                isSelected = selectedTab == "Progress",
                onClick = { onTabSelected("Progress") }
            )
        }
    }
}

@Composable
private fun AnalysisNavItem(
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

@Preview(showBackground = true)
@Composable
fun AiHealthAnalysisScreenPreview() {
    AiHealthAnalysisScreen()
}
