package com.simats.enhancedcyclecareai.ui.cycle

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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
import com.simats.enhancedcyclecareai.ui.theme.MintCardBg
import com.simats.enhancedcyclecareai.ui.theme.PrimaryPurple
import com.simats.enhancedcyclecareai.ui.theme.PrivacyCardBg
import com.simats.enhancedcyclecareai.ui.theme.SoftPinkBg
import com.simats.enhancedcyclecareai.ui.theme.SplashBackground
import com.simats.enhancedcyclecareai.ui.theme.SubtitleMuted
import com.simats.enhancedcyclecareai.ui.theme.TealAccent
import com.simats.enhancedcyclecareai.ui.theme.TitleDark

@Composable
fun CycleTrackingScreen(
    modifier: Modifier = Modifier,
    viewModel: CycleTrackingViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    onNavigateTab: (String) -> Unit = {}
) {
    val monthYearText by viewModel.monthYearText.collectAsState()
    val currentCycleDay by viewModel.currentCycleDay.collectAsState()
    val typicalCycleLength by viewModel.typicalCycleLength.collectAsState()
    val periodStartText by viewModel.periodStartText.collectAsState()
    val nextPeriodText by viewModel.nextPeriodText.collectAsState()
    val todayDay by viewModel.todayDay.collectAsState()
    val periodDays by viewModel.periodDays.collectAsState()
    val selectedTab by viewModel.selectedTab.collectAsState()

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
                    .verticalScroll(scrollState)
            ) {
                Spacer(modifier = Modifier.height(12.dp))

                // 1. Top Header Bar
                CycleTrackingHeader(
                    onBackClick = {
                        viewModel.onBackClicked()
                        onNavigateBack()
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 2. Calendar View Card
                CalendarCard(
                    monthYearText = monthYearText,
                    todayDay = todayDay,
                    periodDays = periodDays,
                    onDateClick = viewModel::onDateSelected
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 3. Cycle Stats Cards (2 Cards)
                CycleStatsRow(
                    cycleDay = currentCycleDay,
                    periodStartText = periodStartText,
                    typicalLength = typicalCycleLength
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 4. Next Period Estimate Banner
                NextPeriodBanner(nextPeriodText = nextPeriodText)

                Spacer(modifier = Modifier.height(16.dp))

                // 5. Start/End Period Action Buttons
                PeriodActionButtons(
                    onStartPeriod = viewModel::onStartPeriod,
                    onEndPeriod = viewModel::onEndPeriod
                )

                Spacer(modifier = Modifier.height(12.dp))

                // 6. Add Record Button
                AddRecordButton(onAddRecord = viewModel::onAddRecord)

                Spacer(modifier = Modifier.height(20.dp))

                // 7. Previous Cycle Summary Card
                PreviousCycleCard()

                Spacer(modifier = Modifier.height(16.dp))

                // 8. Disclaimer Note Card
                CycleDisclaimerCard()

                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        // 9. Bottom Navigation Bar (Track Tab Selected)
        CycleBottomNavigationBar(
            selectedTab = selectedTab,
            onTabSelected = onNavigateTab,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun CycleTrackingHeader(
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
                text = "TRACK · YOUR CYCLE",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryPurple,
                letterSpacing = 1.sp
            )
            Text(
                text = "Menstrual cycle tracking",
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
private fun CalendarCard(
    monthYearText: String,
    todayDay: Int,
    periodDays: Set<Int>,
    onDateClick: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .border(1.dp, FieldBorder, RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Month Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_chevron_left),
                    contentDescription = "Previous Month",
                    tint = SubtitleMuted,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { }
                )
                Text(
                    text = monthYearText,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TitleDark
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_chevron_right),
                    contentDescription = "Next Month",
                    tint = SubtitleMuted,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Weekdays Row
            val weekDays = listOf("M", "T", "W", "T", "F", "S", "S")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                weekDays.forEach { day ->
                    Text(
                        text = day,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = SubtitleMuted,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // October 2026 starts on Thursday (index 3)
            // Days list with offset padding
            val totalDays = 31
            val startOffset = 3 // Thursday
            val daysList = (1..totalDays).toList()

            var dayCounter = 1
            val rowsCount = 5

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                for (row in 0 until rowsCount) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        for (col in 0 until 7) {
                            val cellIndex = row * 7 + col
                            if (cellIndex < startOffset || dayCounter > totalDays) {
                                Spacer(modifier = Modifier.width(32.dp))
                            } else {
                                val currentDay = dayCounter
                                val isToday = currentDay == todayDay
                                val isPeriod = periodDays.contains(currentDay)

                                CalendarDayCell(
                                    day = currentDay,
                                    isToday = isToday,
                                    isPeriod = isPeriod,
                                    onClick = { onDateClick(currentDay) }
                                )
                                dayCounter++
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Legend Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(SoftPinkBg)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Recorded period", fontSize = 11.sp, color = SubtitleMuted)
                }

                Spacer(modifier = Modifier.width(20.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(PrimaryPurple)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Today", fontSize = 11.sp, color = SubtitleMuted)
                }
            }
        }
    }
}

@Composable
private fun CalendarDayCell(
    day: Int,
    isToday: Boolean,
    isPeriod: Boolean,
    onClick: () -> Unit
) {
    val bgColor = when {
        isToday -> PrimaryPurple
        isPeriod -> SoftPinkBg
        else -> Color.Transparent
    }

    val textColor = when {
        isToday -> Color.White
        isPeriod -> Color(0xFFC85A70)
        else -> TitleDark
    }

    Box(
        modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(bgColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$day",
            fontSize = 12.sp,
            fontWeight = if (isToday || isPeriod) FontWeight.Bold else FontWeight.Normal,
            color = textColor
        )
    }
}

@Composable
private fun CycleStatsRow(
    cycleDay: Int,
    periodStartText: String,
    typicalLength: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Card 1: Current Cycle
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(18.dp))
                .background(PrivacyCardBg)
                .padding(14.dp)
        ) {
            Column {
                Text(
                    text = "Day $cycleDay",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryPurple
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Current cycle",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TitleDark
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Started $periodStartText",
                    fontSize = 11.sp,
                    color = SubtitleMuted
                )
            }
        }

        // Card 2: Typical Length
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(18.dp))
                .background(MintCardBg)
                .padding(14.dp)
        ) {
            Column {
                Text(
                    text = "$typicalLength days",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TealAccent
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Typical length",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TitleDark
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Your profile estimate",
                    fontSize = 11.sp,
                    color = SubtitleMuted
                )
            }
        }
    }
}

@Composable
private fun NextPeriodBanner(
    nextPeriodText: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(PrivacyCardBg)
            .padding(14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(BadgeBackground),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_clock_alert),
                    contentDescription = null,
                    tint = PrimaryPurple,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = "Next period - $nextPeriodText",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TitleDark
                )
                Text(
                    text = "Estimated in 15 days · dates may change",
                    fontSize = 12.sp,
                    color = SubtitleMuted
                )
            }
        }
    }
}

@Composable
private fun PeriodActionButtons(
    onStartPeriod: () -> Unit,
    onEndPeriod: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Start Period Button
        Button(
            onClick = onStartPeriod,
            modifier = Modifier
                .weight(1f)
                .height(48.dp),
            shape = RoundedCornerShape(14.dp),
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
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Start Period",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // End Period Button
        OutlinedButton(
            onClick = onEndPeriod,
            modifier = Modifier
                .weight(1f)
                .height(48.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White,
                contentColor = PrimaryPurple
            ),
            border = BorderStroke(1.dp, Color(0xFFE5DEEF))
        ) {
            Text(
                text = "End Period",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryPurple
            )
        }
    }
}

@Composable
private fun AddRecordButton(
    onAddRecord: () -> Unit
) {
    OutlinedButton(
        onClick = onAddRecord,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.White,
            contentColor = PrimaryPurple
        ),
        border = BorderStroke(1.dp, Color(0xFFE5DEEF))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = null,
                tint = PrimaryPurple,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Add Record",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryPurple
            )
        }
    }
}

@Composable
private fun PreviousCycleCard() {
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
                text = "Previous cycle",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TitleDark
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "7 Sep - 4 Oct 2026",
                fontSize = 12.sp,
                color = SubtitleMuted
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(PrivacyCardBg)
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = "28-day cycle",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = PrimaryPurple
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(SoftPinkBg)
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = "5-period days",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFFC85A70)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Moderate flow · cramps and bloating recorded. Tap a date to review its details.",
                fontSize = 12.sp,
                color = SubtitleMuted,
                lineHeight = 17.sp
            )
        }
    }
}

@Composable
private fun CycleDisclaimerCard() {
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
                text = "Record actual bleeding days. Calendar estimates are for awareness, not contraception.",
                fontSize = 11.sp,
                color = PrimaryPurple,
                lineHeight = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun CycleBottomNavigationBar(
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
            CycleNavItem(
                label = "Home",
                iconRes = R.drawable.ic_home_tab,
                isSelected = selectedTab == "Home",
                onClick = { onTabSelected("Home") }
            )
            CycleNavItem(
                label = "Track",
                iconRes = R.drawable.ic_calendar,
                isSelected = selectedTab == "Track",
                onClick = { onTabSelected("Track") }
            )
            CycleNavItem(
                label = "Insights",
                iconRes = R.drawable.ic_sparkles,
                isSelected = selectedTab == "Insights",
                onClick = { onTabSelected("Insights") }
            )
            CycleNavItem(
                label = "Nutrition",
                iconRes = R.drawable.ic_leaf,
                isSelected = selectedTab == "Nutrition",
                onClick = { onTabSelected("Nutrition") }
            )
            CycleNavItem(
                label = "Progress",
                iconRes = R.drawable.ic_progress_tab,
                isSelected = selectedTab == "Progress",
                onClick = { onTabSelected("Progress") }
            )
        }
    }
}

@Composable
private fun CycleNavItem(
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
fun CycleTrackingScreenPreview() {
    CycleTrackingScreen()
}
