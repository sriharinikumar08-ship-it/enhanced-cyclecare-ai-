package com.simats.enhancedcyclecareai.ui.lifestyle

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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
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
import com.simats.enhancedcyclecareai.ui.theme.PrivacyCardBorder
import com.simats.enhancedcyclecareai.ui.theme.SplashBackground
import com.simats.enhancedcyclecareai.ui.theme.SubtitleMuted
import com.simats.enhancedcyclecareai.ui.theme.TaglineMuted
import com.simats.enhancedcyclecareai.ui.theme.TealAccent
import com.simats.enhancedcyclecareai.ui.theme.TitleDark

@Composable
fun LifestyleTrackingScreen(
    modifier: Modifier = Modifier,
    viewModel: LifestyleTrackingViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    onNavigateTab: (String) -> Unit = {}
) {
    val dateText by viewModel.dateText.collectAsState()
    val sleepHours by viewModel.sleepHours.collectAsState()
    val stressLevel by viewModel.stressLevel.collectAsState()
    val selectedActivity by viewModel.selectedActivity.collectAsState()
    val activeMinutes by viewModel.activeMinutes.collectAsState()
    val hydrationGlasses by viewModel.hydrationGlasses.collectAsState()
    val otherObservations by viewModel.otherObservations.collectAsState()
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
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(12.dp))

                // 1. Header Bar
                LifestyleHeader(
                    onBackClick = {
                        viewModel.onBackClicked()
                        onNavigateBack()
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 2. Date Field
                LifestyleInputField(
                    label = "Date",
                    value = dateText,
                    onValueChange = viewModel::onDateChange,
                    trailingIconRes = R.drawable.ic_calendar
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 3. Rest & Recovery Card
                RestRecoveryCard(
                    sleepHours = sleepHours,
                    onSleepHoursChange = viewModel::onSleepHoursChange
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 4. Stress Today Card
                StressLevelCard(
                    stressLevel = stressLevel,
                    onStressChange = viewModel::onStressLevelChange
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 5. Physical Activity Card
                PhysicalActivityCard(
                    selectedActivity = selectedActivity,
                    onActivityChange = viewModel::onActivityChange,
                    activeMinutes = activeMinutes,
                    onMinutesChange = viewModel::onActiveMinutesChange
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 6. Hydration Card
                HydrationCard(
                    hydrationGlasses = hydrationGlasses,
                    onAdd = viewModel::addHydration,
                    onRemove = viewModel::removeHydration
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 7. Other Observations Field
                Text(
                    text = "Other observations · optional",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = TitleDark
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = otherObservations,
                    onValueChange = viewModel::onOtherObservationsChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = PrimaryPurple,
                        unfocusedBorderColor = FieldBorder,
                        focusedTextColor = TitleDark,
                        unfocusedTextColor = TitleDark
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 8. Disclaimer Card
                LifestyleDisclaimerCard()

                Spacer(modifier = Modifier.height(20.dp))

                // 9. Save & Continue Button
                Button(
                    onClick = {
                        viewModel.onSaveAndContinue()
                        onNavigateBack()
                    },
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
                            text = "Save & Continue",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        // 10. Bottom Navigation Bar
        LifestyleBottomNavigationBar(
            selectedTab = selectedTab,
            onTabSelected = onNavigateTab,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun LifestyleHeader(
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
                text = "TRACK · DAILY RHYTHM",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryPurple,
                letterSpacing = 1.sp
            )
            Text(
                text = "Lifestyle tracking",
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
private fun RestRecoveryCard(
    sleepHours: Float,
    onSleepHoursChange: (Float) -> Unit
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_lifestyle),
                    contentDescription = null,
                    tint = PrimaryPurple,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Rest & recovery",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TitleDark
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sleep Hours",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = TitleDark
                )
                Text(
                    text = "$sleepHours hours",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryPurple
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Slider(
                value = sleepHours,
                onValueChange = onSleepHoursChange,
                valueRange = 0f..12f,
                colors = SliderDefaults.colors(
                    thumbColor = PrimaryPurple,
                    activeTrackColor = PrimaryPurple,
                    inactiveTrackColor = Color(0xFFE8DEF2)
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "0 hours", fontSize = 11.sp, color = SubtitleMuted)
                Text(text = "12 hours", fontSize = 11.sp, color = SubtitleMuted)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Last night's sleep. An estimate is okay.",
                fontSize = 12.sp,
                color = SubtitleMuted
            )
        }
    }
}

@Composable
private fun StressLevelCard(
    stressLevel: Float,
    onStressChange: (Float) -> Unit
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_sparkles),
                    contentDescription = null,
                    tint = PrimaryPurple,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Your stress today",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TitleDark
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Stress Level",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = TitleDark
                )
                Text(
                    text = "${stressLevel.toInt()} / 5 · Moderate",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryPurple
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Slider(
                value = stressLevel,
                onValueChange = onStressChange,
                valueRange = 1f..5f,
                colors = SliderDefaults.colors(
                    thumbColor = PrimaryPurple,
                    activeTrackColor = PrimaryPurple,
                    inactiveTrackColor = Color(0xFFE8DEF2)
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Very low", fontSize = 11.sp, color = SubtitleMuted)
                Text(text = "Very high", fontSize = 11.sp, color = SubtitleMuted)
            }
        }
    }
}

@Composable
private fun PhysicalActivityCard(
    selectedActivity: String,
    onActivityChange: (String) -> Unit,
    activeMinutes: String,
    onMinutesChange: (String) -> Unit
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
                text = "Physical Activity",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TitleDark
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val activities = listOf("Walking", "Yoga", "Workout", "Rest day")
                activities.forEach { activity ->
                    val isSelected = activity == selectedActivity
                    ActivityChip(
                        label = activity,
                        isSelected = isSelected,
                        onClick = { onActivityChange(activity) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Active minutes",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = TitleDark
            )

            Spacer(modifier = Modifier.height(6.dp))

            OutlinedTextField(
                value = activeMinutes,
                onValueChange = onMinutesChange,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = PrimaryPurple,
                    unfocusedBorderColor = FieldBorder,
                    focusedTextColor = TitleDark,
                    unfocusedTextColor = TitleDark
                )
            )
        }
    }
}

@Composable
private fun ActivityChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val bgColor = if (isSelected) BadgeBackground else Color.White
    val borderColor = if (isSelected) PrimaryPurple else FieldBorder
    val textColor = if (isSelected) PrimaryPurple else SubtitleMuted

    Row(
        modifier = Modifier
            .clip(CircleShape)
            .background(bgColor)
            .border(1.dp, borderColor, CircleShape)
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isSelected) {
            Icon(
                painter = painterResource(id = R.drawable.ic_check),
                contentDescription = null,
                tint = PrimaryPurple,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
            color = textColor
        )
    }
}

@Composable
private fun HydrationCard(
    hydrationGlasses: Int,
    onAdd: () -> Unit,
    onRemove: () -> Unit
) {
    val liters = hydrationGlasses * 0.25f

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(MintCardBg)
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_droplet),
                        contentDescription = null,
                        tint = TealAccent,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Hydration",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TitleDark
                    )
                }

                Text(
                    text = "$liters / 2 L",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TealAccent
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            LinearProgressIndicator(
                progress = { (hydrationGlasses / 8f).coerceIn(0f, 1f) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(CircleShape),
                color = TealAccent,
                trackColor = Color(0xFFC8E6C9)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "$hydrationGlasses of 8 glasses · your personal reminder goal",
                fontSize = 12.sp,
                color = SubtitleMuted
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                // Remove 250ml
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(1.dp, FieldBorder, CircleShape)
                        .clickable { onRemove() }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(text = "- 250 ml", fontSize = 12.sp, color = TitleDark)
                }

                // Add 250ml
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(BadgeBackground)
                        .border(1.dp, PrimaryPurple, CircleShape)
                        .clickable { onAdd() }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_check),
                            contentDescription = null,
                            tint = PrimaryPurple,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "+ 250 ml", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PrimaryPurple)
                    }
                }
            }
        }
    }
}

@Composable
private fun LifestyleInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    trailingIconRes: Int
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = TitleDark
        )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = PrimaryPurple,
                unfocusedBorderColor = FieldBorder,
                focusedTextColor = TitleDark,
                unfocusedTextColor = TitleDark
            ),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = trailingIconRes),
                    contentDescription = null,
                    tint = SubtitleMuted,
                    modifier = Modifier.size(20.dp)
                )
            }
        )
    }
}

@Composable
private fun LifestyleDisclaimerCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(PrivacyCardBg)
            .border(BorderStroke(1.dp, PrivacyCardBorder), RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_sliders),
                contentDescription = null,
                tint = PrimaryPurple,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Lifestyle entries may be inputs to personalized pattern analysis. They can reveal observations, not prove causes.",
                fontSize = 11.sp,
                color = PrimaryPurple,
                lineHeight = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun LifestyleBottomNavigationBar(
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
            LifestyleNavItem(
                label = "Home",
                iconRes = R.drawable.ic_home_tab,
                isSelected = selectedTab == "Home",
                onClick = { onTabSelected("Home") }
            )
            LifestyleNavItem(
                label = "Track",
                iconRes = R.drawable.ic_calendar,
                isSelected = selectedTab == "Track",
                onClick = { onTabSelected("Track") }
            )
            LifestyleNavItem(
                label = "Insights",
                iconRes = R.drawable.ic_sparkles,
                isSelected = selectedTab == "Insights",
                onClick = { onTabSelected("Insights") }
            )
            LifestyleNavItem(
                label = "Nutrition",
                iconRes = R.drawable.ic_leaf,
                isSelected = selectedTab == "Nutrition",
                onClick = { onTabSelected("Nutrition") }
            )
            LifestyleNavItem(
                label = "Progress",
                iconRes = R.drawable.ic_progress_tab,
                isSelected = selectedTab == "Progress",
                onClick = { onTabSelected("Progress") }
            )
        }
    }
}

@Composable
private fun LifestyleNavItem(
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
fun LifestyleTrackingScreenPreview() {
    LifestyleTrackingScreen()
}
