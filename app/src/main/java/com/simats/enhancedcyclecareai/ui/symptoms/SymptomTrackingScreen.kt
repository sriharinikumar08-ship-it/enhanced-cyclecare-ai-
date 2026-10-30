package com.simats.enhancedcyclecareai.ui.symptoms

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
import com.simats.enhancedcyclecareai.ui.theme.PrimaryPurple
import com.simats.enhancedcyclecareai.ui.theme.PrivacyCardBg
import com.simats.enhancedcyclecareai.ui.theme.PrivacyCardBorder
import com.simats.enhancedcyclecareai.ui.theme.SplashBackground
import com.simats.enhancedcyclecareai.ui.theme.SubtitleMuted
import com.simats.enhancedcyclecareai.ui.theme.TitleDark

data class SymptomOption(
    val name: String,
    val iconRes: Int
)

@Composable
fun SymptomTrackingScreen(
    modifier: Modifier = Modifier,
    viewModel: SymptomTrackingViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    onNavigateTab: (String) -> Unit = {}
) {
    val dateText by viewModel.dateText.collectAsState()
    val selectedSymptoms by viewModel.selectedSymptoms.collectAsState()
    val focusedSymptom by viewModel.focusedSymptom.collectAsState()
    val severityLevel by viewModel.severityLevel.collectAsState()
    val durationText by viewModel.durationText.collectAsState()
    val notesText by viewModel.notesText.collectAsState()
    val selectedTab by viewModel.selectedTab.collectAsState()

    val scrollState = rememberScrollState()

    val symptomList = listOf(
        SymptomOption("Cramps", R.drawable.ic_cramps),
        SymptomOption("Headache", R.drawable.ic_headache),
        SymptomOption("Fatigue", R.drawable.ic_fatigue),
        SymptomOption("Mood Changes", R.drawable.ic_mood),
        SymptomOption("Bloating", R.drawable.ic_symptoms),
        SymptomOption("Breast Tenderness", R.drawable.ic_symptoms),
        SymptomOption("Nausea", R.drawable.ic_mood),
        SymptomOption("Other Symptoms", R.drawable.ic_plus)
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
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(12.dp))

                // 1. Top Header Bar
                SymptomHeader(
                    onBackClick = {
                        viewModel.onBackClicked()
                        onNavigateBack()
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 2. Subtitle
                Text(
                    text = "How did you feel? Select what you noticed, even if it seems small.",
                    fontSize = 14.sp,
                    color = SubtitleMuted,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 3. Date Selector Field
                SymptomInputField(
                    label = "Date",
                    value = dateText,
                    onValueChange = viewModel::onDateChange,
                    trailingIconRes = R.drawable.ic_calendar
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 4. Symptom Grid (2 Columns)
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    for (i in symptomList.indices step 2) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            val item1 = symptomList[i]
                            val isSelected1 = selectedSymptoms.contains(item1.name)

                            SymptomCardItem(
                                name = item1.name,
                                iconRes = item1.iconRes,
                                isSelected = isSelected1,
                                modifier = Modifier.weight(1f),
                                onClick = { viewModel.toggleSymptom(item1.name) }
                            )

                            if (i + 1 < symptomList.size) {
                                val item2 = symptomList[i + 1]
                                val isSelected2 = selectedSymptoms.contains(item2.name)

                                SymptomCardItem(
                                    name = item2.name,
                                    iconRes = item2.iconRes,
                                    isSelected = isSelected2,
                                    modifier = Modifier.weight(1f),
                                    onClick = { viewModel.toggleSymptom(item2.name) }
                                )
                            } else {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // 5. Symptom Severity Detail Card
                if (focusedSymptom.isNotEmpty()) {
                    SymptomSeverityCard(
                        symptomName = focusedSymptom,
                        severityLevel = severityLevel,
                        onSeverityChange = viewModel::onSeverityChange
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }

                // 6. Duration Field
                SymptomInputField(
                    label = "Duration",
                    value = durationText,
                    onValueChange = viewModel::onDurationChange,
                    trailingIconRes = R.drawable.ic_clock
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 7. Notes Field
                Text(
                    text = "Notes · optional",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = TitleDark
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = notesText,
                    onValueChange = viewModel::onNotesChange,
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

                // 8. Warning/Disclaimer Card
                SymptomDisclaimerCard()

                Spacer(modifier = Modifier.height(20.dp))

                // 9. Save Symptoms Button
                Button(
                    onClick = {
                        viewModel.onSaveSymptoms()
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
                            painter = painterResource(id = R.drawable.ic_check),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Save Symptoms",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        // 10. Bottom Navigation Bar
        SymptomBottomNavigationBar(
            selectedTab = selectedTab,
            onTabSelected = onNavigateTab,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun SymptomHeader(
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
                text = "TRACK · CHECK IN",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryPurple,
                letterSpacing = 1.sp
            )
            Text(
                text = "Symptom tracking",
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
private fun SymptomCardItem(
    name: String,
    iconRes: Int,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val bgColor = if (isSelected) BadgeBackground else Color.White
    val borderColor = if (isSelected) PrimaryPurple else FieldBorder
    val textColor = if (isSelected) PrimaryPurple else TitleDark

    Box(
        modifier = modifier
            .height(56.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(bgColor)
            .border(1.dp, borderColor, RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = name,
                    tint = if (isSelected) PrimaryPurple else SubtitleMuted,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = name,
                    fontSize = 13.sp,
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                    color = textColor
                )
            }

            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .clip(CircleShape)
                        .border(1.dp, PrimaryPurple, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_check),
                        contentDescription = null,
                        tint = PrimaryPurple,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun SymptomSeverityCard(
    symptomName: String,
    severityLevel: Float,
    onSeverityChange: (Float) -> Unit
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
                text = symptomName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TitleDark
            )
            Text(
                text = "Set severity for this selected symptom",
                fontSize = 12.sp,
                color = SubtitleMuted
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Severity",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = TitleDark
                )
                Text(
                    text = "${severityLevel.toInt()} / 10 · Mild",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryPurple
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Slider(
                value = severityLevel,
                onValueChange = onSeverityChange,
                valueRange = 0f..10f,
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
                Text(text = "Barely noticeable", fontSize = 11.sp, color = SubtitleMuted)
                Text(text = "Severe", fontSize = 11.sp, color = SubtitleMuted)
            }
        }
    }
}

@Composable
private fun SymptomInputField(
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
private fun SymptomDisclaimerCard() {
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
                painter = painterResource(id = R.drawable.ic_heart_privacy),
                contentDescription = null,
                tint = PrimaryPurple,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "If symptoms are sudden, severe, or worrying, contact a healthcare professional rather than waiting for an app insight.",
                fontSize = 11.sp,
                color = PrimaryPurple,
                lineHeight = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun SymptomBottomNavigationBar(
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
            SymptomNavItem(
                label = "Home",
                iconRes = R.drawable.ic_home_tab,
                isSelected = selectedTab == "Home",
                onClick = { onTabSelected("Home") }
            )
            SymptomNavItem(
                label = "Track",
                iconRes = R.drawable.ic_calendar,
                isSelected = selectedTab == "Track",
                onClick = { onTabSelected("Track") }
            )
            SymptomNavItem(
                label = "Insights",
                iconRes = R.drawable.ic_sparkles,
                isSelected = selectedTab == "Insights",
                onClick = { onTabSelected("Insights") }
            )
            SymptomNavItem(
                label = "Nutrition",
                iconRes = R.drawable.ic_leaf,
                isSelected = selectedTab == "Nutrition",
                onClick = { onTabSelected("Nutrition") }
            )
            SymptomNavItem(
                label = "Progress",
                iconRes = R.drawable.ic_progress_tab,
                isSelected = selectedTab == "Progress",
                onClick = { onTabSelected("Progress") }
            )
        }
    }
}

@Composable
private fun SymptomNavItem(
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
fun SymptomTrackingScreenPreview() {
    SymptomTrackingScreen()
}
