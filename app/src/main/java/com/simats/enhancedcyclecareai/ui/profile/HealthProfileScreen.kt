package com.simats.enhancedcyclecareai.ui.profile

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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.simats.enhancedcyclecareai.ui.theme.TaglineMuted
import com.simats.enhancedcyclecareai.ui.theme.TitleDark

@Composable
fun HealthProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: HealthProfileViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    onNavigateNext: () -> Unit = {}
) {
    val age by viewModel.age.collectAsState()
    val cycleLength by viewModel.cycleLength.collectAsState()
    val periodLength by viewModel.periodLength.collectAsState()
    val mostRecentPeriod by viewModel.mostRecentPeriod.collectAsState()
    val dietaryPreference by viewModel.dietaryPreference.collectAsState()
    val foodAllergies by viewModel.foodAllergies.collectAsState()
    val foodRestrictions by viewModel.foodRestrictions.collectAsState()
    val preferredMealStyle by viewModel.preferredMealStyle.collectAsState()

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
                .padding(horizontal = 20.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // 1. Header Bar
            HealthProfileHeader(
                onBackClick = {
                    viewModel.onBackClicked()
                    onNavigateBack()
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 2. Step Progress Bar
            LinearProgressIndicator(
                progress = { 0.5f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(CircleShape),
                color = PrimaryPurple,
                trackColor = Color(0xFFE8DEF2)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 3. Subtitle
            Text(
                text = "Just enough context to make tracking and meal ideas more relevant to you.",
                fontSize = 14.sp,
                color = SubtitleMuted,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 4. Age Dropdown
            ProfileDropdownField(
                label = "Age",
                selectedOption = age,
                options = listOf("18 years", "21 years", "24 years", "28 years", "32 years"),
                onOptionSelected = viewModel::onAgeChange
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 5. Your Typical Cycle Section
            Text(
                text = "Your typical cycle",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TitleDark
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    ProfileDropdownField(
                        label = "Cycle length",
                        selectedOption = cycleLength,
                        options = listOf("28 days", "30 days", "32 days", "35 days"),
                        onOptionSelected = viewModel::onCycleLengthChange
                    )
                }
                Box(modifier = Modifier.weight(1f)) {
                    ProfileDropdownField(
                        label = "Period length",
                        selectedOption = periodLength,
                        options = listOf("3 days", "4 days", "5 days", "6 days", "7 days"),
                        onOptionSelected = viewModel::onPeriodLengthChange
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Most recent period started
            ProfileInputField(
                label = "Most recent period started",
                value = mostRecentPeriod,
                trailingIconRes = R.drawable.ic_calendar
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 6. Dietary Preference Section
            Text(
                text = "Dietary preference",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = TitleDark
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val dietaryOptions = listOf("Vegetarian", "Non-vegetarian", "Vegan")
                dietaryOptions.forEach { option ->
                    val isSelected = option == dietaryPreference
                    DietaryChip(
                        label = option,
                        isSelected = isSelected,
                        onClick = { viewModel.onDietaryPreferenceChange(option) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 7. Food Allergies
            ProfileDropdownField(
                label = "Food allergies",
                selectedOption = foodAllergies,
                options = listOf("None", "Peanuts", "Dairy", "Gluten", "Soy", "Shellfish"),
                onOptionSelected = viewModel::onFoodAllergiesChange,
                helperText = "We'll flag this when suggesting meals."
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 8. Food Restrictions
            ProfileDropdownField(
                label = "Food restrictions",
                selectedOption = foodRestrictions,
                options = listOf("None", "No eggs", "Low sodium", "Keto", "Halal", "Jain"),
                onOptionSelected = viewModel::onFoodRestrictionsChange
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 9. Preferred Meal Style / Cuisine
            ProfileDropdownField(
                label = "Preferred meal style / cuisine",
                selectedOption = preferredMealStyle,
                options = listOf(
                    "Indian · Home-style · Quick meals",
                    "Mediterranean · Balanced",
                    "Asian · Fresh · Low-carb",
                    "Global · Plant-focused"
                ),
                onOptionSelected = viewModel::onPreferredMealStyleChange
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 10. Notice Card
            ProfileNoticeCard()

            Spacer(modifier = Modifier.height(20.dp))

            // 11. Save & Continue Button
            Button(
                onClick = {
                    viewModel.onSaveAndContinueClicked()
                    onNavigateNext()
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
}

@Composable
private fun HealthProfileHeader(
    onBackClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Back Button
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

        // Tag & Title
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp)
        ) {
            Text(
                text = "MAKE IT PERSONAL · STEP 1 OF 2",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryPurple,
                letterSpacing = 1.sp
            )
            Text(
                text = "Health & dietary profile",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TitleDark
            )
        }

        // Right Options Button
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
private fun ProfileDropdownField(
    label: String,
    selectedOption: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit,
    helperText: String? = null
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = TitleDark
        )

        Spacer(modifier = Modifier.height(6.dp))

        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .border(1.dp, FieldBorder, RoundedCornerShape(12.dp))
                    .clickable { expanded = true }
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedOption,
                    fontSize = 14.sp,
                    color = TitleDark
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_chevron_down),
                    contentDescription = null,
                    tint = SubtitleMuted,
                    modifier = Modifier.size(20.dp)
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(Color.White)
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(text = option, color = TitleDark) },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }

        if (helperText != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = helperText,
                fontSize = 12.sp,
                color = SubtitleMuted
            )
        }
    }
}

@Composable
private fun ProfileInputField(
    label: String,
    value: String,
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .border(1.dp, FieldBorder, RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = value,
                fontSize = 14.sp,
                color = TitleDark
            )
            Icon(
                painter = painterResource(id = trailingIconRes),
                contentDescription = null,
                tint = SubtitleMuted,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun DietaryChip(
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
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isSelected) {
            Icon(
                painter = painterResource(id = R.drawable.ic_check),
                contentDescription = null,
                tint = PrimaryPurple,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
        }
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
            color = textColor
        )
    }
}

@Composable
private fun ProfileNoticeCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(PrivacyCardBg)
            .border(BorderStroke(1.dp, PrivacyCardBorder), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_clipboard_info),
                contentDescription = "Notice",
                tint = PrimaryPurple,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "These details support personalized tracking. Skip anything you're unsure about and edit it later.",
                fontSize = 12.sp,
                color = PrimaryPurple,
                lineHeight = 17.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HealthProfileScreenPreview() {
    HealthProfileScreen()
}
