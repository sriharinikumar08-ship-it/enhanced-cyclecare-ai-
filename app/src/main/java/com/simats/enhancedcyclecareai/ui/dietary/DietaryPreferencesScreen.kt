package com.simats.enhancedcyclecareai.ui.dietary

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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
import com.simats.enhancedcyclecareai.ui.theme.SoftPinkBg
import com.simats.enhancedcyclecareai.ui.theme.SplashBackground
import com.simats.enhancedcyclecareai.ui.theme.SubtitleMuted
import com.simats.enhancedcyclecareai.ui.theme.TitleDark

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DietaryPreferencesScreen(
    modifier: Modifier = Modifier,
    viewModel: DietaryPreferencesViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    onNavigateHome: () -> Unit = {},
    onNavigateTab: (String) -> Unit = {}
) {
    val eatingStyle by viewModel.eatingStyle.collectAsState()
    val allergies by viewModel.allergies.collectAsState()
    val dislikedFoods by viewModel.dislikedFoods.collectAsState()
    val restrictions by viewModel.restrictions.collectAsState()
    val preferredCuisine by viewModel.preferredCuisine.collectAsState()
    val selectedPreferences by viewModel.selectedPreferences.collectAsState()
    val prepTime by viewModel.prepTime.collectAsState()
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
                DietaryHeader(
                    onBackClick = {
                        viewModel.onBackClicked()
                        onNavigateBack()
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 2. Subtitle
                Text(
                    text = "Good food guidance should fit your plate, your preferences, and your day.",
                    fontSize = 14.sp,
                    color = SubtitleMuted,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 3. How do you prefer to eat?
                Text(
                    text = "How do you prefer to eat?",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = TitleDark
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val eatingOptions = listOf("Vegetarian", "Non-vegetarian", "Vegan")
                    eatingOptions.forEach { option ->
                        val isSelected = option == eatingStyle
                        DietaryChip(
                            label = option,
                            isSelected = isSelected,
                            onClick = { viewModel.onEatingStyleChange(option) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // 4. Allergies Card
                AllergiesCard(
                    allergies = allergies,
                    onToggleAllergy = viewModel::toggleAllergy
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 5. Disliked foods Dropdown
                DietaryDropdownField(
                    label = "Disliked foods",
                    selectedOption = dislikedFoods,
                    options = listOf("None", "Mushrooms", "Eggplant", "Bitter gourd", "Seaweed"),
                    onOptionSelected = viewModel::onDislikedFoodsChange
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 6. Restrictions Dropdown
                DietaryDropdownField(
                    label = "Restrictions",
                    selectedOption = restrictions,
                    options = listOf("None", "No eggs", "Low sodium", "Gluten-free", "Dairy-free"),
                    onOptionSelected = viewModel::onRestrictionsChange
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 7. Preferred cuisine Dropdown
                DietaryDropdownField(
                    label = "Preferred cuisine",
                    selectedOption = preferredCuisine,
                    options = listOf("Indian · Home-style", "Mediterranean", "Asian Fusion", "Global Comfort"),
                    onOptionSelected = viewModel::onPreferredCuisineChange
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 8. What works for you? Section
                Text(
                    text = "What works for you?",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = TitleDark
                )

                Spacer(modifier = Modifier.height(8.dp))

                val preferenceOptions = listOf("Quick meals", "Budget-friendly", "Batch cooking", "Mild spice", "High variety")
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    preferenceOptions.forEach { pref ->
                        val isSelected = selectedPreferences.contains(pref)
                        DietaryChip(
                            label = pref,
                            isSelected = isSelected,
                            onClick = { viewModel.togglePreference(pref) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // 9. Typical preparation time Dropdown
                DietaryDropdownField(
                    label = "Typical preparation time",
                    selectedOption = prepTime,
                    options = listOf("Under 15 minutes", "15–30 minutes", "30–45 minutes", "45+ minutes"),
                    onOptionSelected = viewModel::onPrepTimeChange,
                    trailingIconRes = R.drawable.ic_clock
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 10. Notice Card
                DietaryNoticeCard()

                Spacer(modifier = Modifier.height(20.dp))

                // 11. Action Buttons
                Button(
                    onClick = {
                        viewModel.onSavePreferences()
                        onNavigateHome()
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
                            text = "Save Preferences",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedButton(
                    onClick = {
                        viewModel.onFinishLater()
                        onNavigateHome()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.White,
                        contentColor = PrimaryPurple
                    ),
                    border = BorderStroke(1.dp, Color(0xFFE5DEEF))
                ) {
                    Text(
                        text = "I'll finish this later",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = PrimaryPurple
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        // 12. Bottom Navigation Bar
        DietaryBottomNavigationBar(
            selectedTab = selectedTab,
            onTabSelected = onNavigateTab,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun DietaryHeader(
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
                text = "MAKE IT PERSONAL · STEP 2 OF 2",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryPurple,
                letterSpacing = 1.sp
            )
            Text(
                text = "Dietary preferences",
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
private fun AllergiesCard(
    allergies: Set<String>,
    onToggleAllergy: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(SoftPinkBg)
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = "Allergies",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TitleDark
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                allergies.forEach { allergy ->
                    DietaryChip(
                        label = allergy,
                        isSelected = true,
                        onClick = { onToggleAllergy(allergy) }
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(1.dp, FieldBorder, CircleShape)
                        .clickable { }
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "Add allergy +",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = PrimaryPurple
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Peanuts will be excluded from suggestions. Always check labels and cross-contact information.",
                fontSize = 12.sp,
                color = SubtitleMuted,
                lineHeight = 17.sp
            )
        }
    }
}

@Composable
private fun DietaryDropdownField(
    label: String,
    selectedOption: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit,
    trailingIconRes: Int = R.drawable.ic_chevron_down
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
                    painter = painterResource(id = trailingIconRes),
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
            .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isSelected) {
            Icon(
                painter = painterResource(id = R.drawable.ic_check),
                contentDescription = null,
                tint = PrimaryPurple,
                modifier = Modifier.size(14.dp)
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
private fun DietaryNoticeCard() {
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
                text = "You can edit these preferences anytime in Nutrition. Suggestions are flexible, not a prescribed diet.",
                fontSize = 11.sp,
                color = PrimaryPurple,
                lineHeight = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun DietaryBottomNavigationBar(
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
            DietaryNavItem(
                label = "Home",
                iconRes = R.drawable.ic_home_tab,
                isSelected = selectedTab == "Home",
                onClick = { onTabSelected("Home") }
            )
            DietaryNavItem(
                label = "Track",
                iconRes = R.drawable.ic_calendar,
                isSelected = selectedTab == "Track",
                onClick = { onTabSelected("Track") }
            )
            DietaryNavItem(
                label = "Insights",
                iconRes = R.drawable.ic_sparkles,
                isSelected = selectedTab == "Insights",
                onClick = { onTabSelected("Insights") }
            )
            DietaryNavItem(
                label = "Nutrition",
                iconRes = R.drawable.ic_leaf,
                isSelected = selectedTab == "Nutrition",
                onClick = { onTabSelected("Nutrition") }
            )
            DietaryNavItem(
                label = "Progress",
                iconRes = R.drawable.ic_progress_tab,
                isSelected = selectedTab == "Progress",
                onClick = { onTabSelected("Progress") }
            )
        }
    }
}

@Composable
private fun DietaryNavItem(
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
fun DietaryPreferencesScreenPreview() {
    DietaryPreferencesScreen()
}
