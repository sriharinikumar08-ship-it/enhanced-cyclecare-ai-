package com.simats.enhancedcyclecareai.ui.menstrualpattern

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.simats.enhancedcyclecareai.R
import com.simats.enhancedcyclecareai.ui.theme.BadgeBackground
import com.simats.enhancedcyclecareai.ui.theme.FieldBorder
import com.simats.enhancedcyclecareai.ui.theme.PrimaryPurple
import com.simats.enhancedcyclecareai.ui.theme.SplashBackground
import com.simats.enhancedcyclecareai.ui.theme.SubtitleMuted
import com.simats.enhancedcyclecareai.ui.theme.TitleDark

@Composable
fun GeneralMenstrualPatternScreen(
    modifier: Modifier = Modifier,
    viewModel: GeneralMenstrualPatternViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    onNavigateTab: (String) -> Unit = {},
    onNavigateToGuidance: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedTab by viewModel.selectedTab.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is GeneralMenstrualPatternUiEvent.NavigateBack -> onNavigateBack()
                is GeneralMenstrualPatternUiEvent.ShowToast -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                is GeneralMenstrualPatternUiEvent.ViewPersonalizedGuidance -> onNavigateToGuidance()
            }
        }
    }

    val scrollState = rememberScrollState()

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
                MenstrualHeader(
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

                // 3. Two Summary Cards Side by Side
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SummaryMetricCard(
                        modifier = Modifier.weight(1f),
                        metric = uiState.cycleLengthMetric,
                        subtitle = uiState.cycleLengthSub,
                        footer = uiState.cycleLengthFooter
                    )
                    SummaryMetricCard(
                        modifier = Modifier.weight(1f),
                        metric = uiState.periodDurationMetric,
                        subtitle = uiState.periodDurationSub,
                        footer = uiState.periodDurationFooter
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 4. Cycle Variation Card with Line Chart
                CycleVariationCard(
                    badgeText = uiState.cycleVariationBadge,
                    description = uiState.cycleVariationDescription
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 5. Recurring Pain & PMS Observations Card
                RecurringPainCard()

                Spacer(modifier = Modifier.height(16.dp))

                // 6. Possible Contributing Context Card
                ContributingContextCard(
                    contextText = uiState.contributingContext
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 7. Practical Next Steps Card
                PracticalNextStepsCard()

                Spacer(modifier = Modifier.height(16.dp))

                // 8. Warning Banner Card
                MenstrualWarningCard(
                    warningText = uiState.warningBannerText
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 9. View Personalized Guidance Button
                Button(
                    onClick = { viewModel.onViewPersonalizedGuidanceClicked() },
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
                            painter = painterResource(id = R.drawable.ic_arrow_right),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "View Personalized Guidance",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        // 10. Bottom Navigation Bar
        MenstrualBottomNavigationBar(
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
private fun MenstrualHeader(
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
private fun SummaryMetricCard(
    modifier: Modifier = Modifier,
    metric: String,
    subtitle: String,
    footer: String
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .background(Color.White)
            .border(1.dp, FieldBorder, RoundedCornerShape(18.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = metric,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryPurple
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = TitleDark
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = footer,
                fontSize = 11.sp,
                color = SubtitleMuted
            )
        }
    }
}

@Composable
private fun CycleVariationCard(
    badgeText: String,
    description: String
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
                text = "Cycle variation",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TitleDark
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Completed cycles ending May-Oct · days",
                fontSize = 12.sp,
                color = SubtitleMuted
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Graph Canvas
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val width = size.width
                    val height = size.height
                    // Values mapped from 20 to 40 range
                    // May: 30, Jun: 30, Jul: 32, Aug: 28, Sep: 38, Oct: 25
                    val values = listOf(30f, 30f, 32f, 28f, 38f, 25f)
                    val minVal = 20f
                    val maxVal = 40f

                    val stepX = width / (values.size - 1)
                    val points = values.mapIndexed { index, value ->
                        val x = index * stepX
                        val y = height - ((value - minVal) / (maxVal - minVal)) * (height - 20f) - 10f
                        Offset(x, y)
                    }

                    // Draw horizontal grid lines
                    val linePaintColor = Color(0xFFE8DEF2)
                    drawLine(linePaintColor, Offset(0f, height * 0.25f), Offset(width, height * 0.25f), strokeWidth = 1.dp.toPx())
                    drawLine(linePaintColor, Offset(0f, height * 0.6f), Offset(width, height * 0.6f), strokeWidth = 1.dp.toPx())
                    drawLine(linePaintColor, Offset(0f, height * 0.95f), Offset(width, height * 0.95f), strokeWidth = 1.dp.toPx())

                    // Draw polyline connecting points
                    val path = Path().apply {
                        moveTo(points[0].x, points[0].y)
                        for (i in 1 until points.size) {
                            lineTo(points[i].x, points[i].y)
                        }
                    }
                    drawPath(
                        path = path,
                        color = PrimaryPurple,
                        style = Stroke(width = 2.5.dp.toPx())
                    )

                    // Draw points
                    points.forEach { pt ->
                        drawCircle(
                            color = PrimaryPurple,
                            radius = 4.dp.toPx(),
                            center = pt
                        )
                        drawCircle(
                            color = Color.White,
                            radius = 2.dp.toPx(),
                            center = pt
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(BadgeBackground)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = badgeText,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = PrimaryPurple
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = description,
                fontSize = 13.sp,
                color = TitleDark,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
private fun RecurringPainCard() {
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
                text = "Recurring pain & PMS observations",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TitleDark
            )
            Spacer(modifier = Modifier.height(14.dp))

            PainItemRow(
                iconRes = R.drawable.ic_cramps,
                title = "Cramps cluster around days 1–2",
                subtitle = "Recorded in 4 of your last 6 periods"
            )

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = FieldBorder, thickness = 0.5.dp)
            Spacer(modifier = Modifier.height(12.dp))

            PainItemRow(
                iconRes = R.drawable.ic_clock,
                title = "Bloating near your period",
                subtitle = "Recorded in 3 of your last 6 cycles"
            )

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = FieldBorder, thickness = 0.5.dp)
            Spacer(modifier = Modifier.height(12.dp))

            PainItemRow(
                iconRes = R.drawable.ic_warning,
                title = "Latest pain peak: 6/10",
                subtitle = "6 Oct · cycle day 2"
            )

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Timing may be correlated with period-related symptoms, but the app cannot determine their cause.",
                fontSize = 12.sp,
                color = SubtitleMuted,
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
private fun PainItemRow(
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
private fun ContributingContextCard(
    contextText: String
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
                text = "Possible contributing context",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TitleDark
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = contextText,
                fontSize = 13.sp,
                color = TitleDark,
                lineHeight = 19.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(BadgeBackground)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "Association is not causation",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = PrimaryPurple
                )
            }
        }
    }
}

@Composable
private fun PracticalNextStepsCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Color(0xFFF4F1FA))
            .border(1.dp, Color(0xFFE8DEF2), RoundedCornerShape(18.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = "Practical next steps",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TitleDark
            )
            Spacer(modifier = Modifier.height(14.dp))

            NextStepRow(
                iconRes = R.drawable.ic_calendar,
                title = "Keep a consistent date record",
                sub = "Add actual period starts and ends."
            )
            Spacer(modifier = Modifier.height(12.dp))
            NextStepRow(
                iconRes = R.drawable.ic_edit,
                title = "Add symptom timing and severity",
                sub = "Especially pain that affects your day."
            )
            Spacer(modifier = Modifier.height(12.dp))
            NextStepRow(
                iconRes = R.drawable.ic_doctor,
                title = "Discuss persistent or disruptive pain",
                sub = "Bring your timeline to a professional."
            )
        }
    }
}

@Composable
private fun NextStepRow(
    iconRes: Int,
    title: String,
    sub: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Color.White),
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
                text = sub,
                fontSize = 12.sp,
                color = SubtitleMuted
            )
        }
    }
}

@Composable
private fun MenstrualWarningCard(
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
private fun MenstrualBottomNavigationBar(
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
            MenstrualNavItem(
                label = "Home",
                iconRes = R.drawable.ic_home_tab,
                isSelected = selectedTab == "Home",
                onClick = { onTabSelected("Home") }
            )
            MenstrualNavItem(
                label = "Track",
                iconRes = R.drawable.ic_calendar,
                isSelected = selectedTab == "Track",
                onClick = { onTabSelected("Track") }
            )
            MenstrualNavItem(
                label = "Insights",
                iconRes = R.drawable.ic_sparkles,
                isSelected = selectedTab == "Insights",
                onClick = { onTabSelected("Insights") }
            )
            MenstrualNavItem(
                label = "Nutrition",
                iconRes = R.drawable.ic_leaf,
                isSelected = selectedTab == "Nutrition",
                onClick = { onTabSelected("Nutrition") }
            )
            MenstrualNavItem(
                label = "Progress",
                iconRes = R.drawable.ic_progress_tab,
                isSelected = selectedTab == "Progress",
                onClick = { onTabSelected("Progress") }
            )
        }
    }
}

@Composable
private fun MenstrualNavItem(
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
