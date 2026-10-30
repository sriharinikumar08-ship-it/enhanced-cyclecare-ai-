package com.simats.enhancedcyclecareai.ui.patterndetection

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
import android.widget.Toast
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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

data class PatternResultCardData(
    val title: String,
    val badgeText: String,
    val badgeBg: Color,
    val badgeTextColor: Color,
    val description: String,
    val footerText: String,
    val iconRes: Int
)

@Composable
fun PatternDetectionScreen(
    modifier: Modifier = Modifier,
    viewModel: PatternDetectionViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    onNavigateTab: (String) -> Unit = {},
    onNavigateToPcos: () -> Unit = {},
    onNavigateToGeneralMenstrualPattern: () -> Unit = {},
    onNavigateToGuidance: () -> Unit = {}
) {
    val lastUpdatedText by viewModel.lastUpdatedText.collectAsState()
    val selectedTab by viewModel.selectedTab.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is PatternDetectionUiEvent.NavigateBack -> onNavigateBack()
                is PatternDetectionUiEvent.ShowToast -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                is PatternDetectionUiEvent.NavigateToGuidance -> onNavigateToGuidance()
                is PatternDetectionUiEvent.NavigateToPcosPattern -> onNavigateToPcos()
                is PatternDetectionUiEvent.NavigateToGeneralMenstrualPattern -> onNavigateToGeneralMenstrualPattern()
            }
        }
    }

    val scrollState = rememberScrollState()

    val patternCards = listOf(
        PatternResultCardData(
            title = "Cycle Pattern",
            badgeText = "Moderate",
            badgeBg = BadgeBackground,
            badgeTextColor = PrimaryPurple,
            description = "Variation across 6 completed cycles. Recorded lengths range from 28 to 38 days.",
            footerText = "6 cycles · Apr–Oct 2026",
            iconRes = R.drawable.ic_calendar
        ),
        PatternResultCardData(
            title = "Symptom Pattern",
            badgeText = "Attention Needed",
            badgeBg = Color(0xFFFEF3C7),
            badgeTextColor = Color(0xFFB45309),
            description = "Cramps recur near your period, with a latest peak of 6/10. Consider discussing persistent pain.",
            footerText = "12 symptom entries this month",
            iconRes = R.drawable.ic_symptoms
        ),
        PatternResultCardData(
            title = "PCOS-Related Pattern Awareness",
            badgeText = "Moderate",
            badgeBg = BadgeBackground,
            badgeTextColor = PrimaryPurple,
            description = "Some cycle variation is worth monitoring. These limited records cannot identify PCOS.",
            footerText = "Limited specificity · more context needed",
            iconRes = R.drawable.ic_sparkles
        ),
        PatternResultCardData(
            title = "Lifestyle Pattern",
            badgeText = "Low",
            badgeBg = SoftGreenBg,
            badgeTextColor = TealAccent,
            description = "No clear recurring association in the available daily logs. This does not mean no health concerns.",
            footerText = "18 daily entries · gaps remain",
            iconRes = R.drawable.ic_lifestyle
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
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(12.dp))

                // 1. Header Bar
                PatternHeader(
                    onBackClick = {
                        viewModel.onBackClicked()
                        onNavigateBack()
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 2. Updated Badge Pill & Subtitle
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(SoftGreenBg)
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = lastUpdatedText,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = TealAccent
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "A starting point for awareness, not a health verdict. Open each explanation to see what informed it.",
                    fontSize = 14.sp,
                    color = SubtitleMuted,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 3. Pattern Result Cards
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    patternCards.forEach { card ->
                        PatternCardItem(
                            card = card,
                            onViewDetails = { viewModel.onViewDetails(card.title) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // 4. Disclaimer Card
                PatternDisclaimerCard()

                Spacer(modifier = Modifier.height(20.dp))

                // 5. Explore Personalized Guidance Button
                Button(
                    onClick = { viewModel.onExploreGuidanceClicked() },
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
                            text = "Explore Personalized Guidance",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        // 6. Bottom Navigation Bar
        PatternBottomNavigationBar(
            selectedTab = selectedTab,
            onTabSelected = onNavigateTab,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun PatternHeader(
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
                text = "AI INSIGHTS · STEP 3 OF 3",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryPurple,
                letterSpacing = 1.sp
            )
            Text(
                text = "Your pattern results",
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
private fun PatternCardItem(
    card: PatternResultCardData,
    onViewDetails: () -> Unit
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
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(SoftPurpleBg),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = card.iconRes),
                            contentDescription = null,
                            tint = PrimaryPurple,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = card.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TitleDark
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(card.badgeBg)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = card.badgeText,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = card.badgeTextColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = card.description,
                fontSize = 13.sp,
                color = TitleDark,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = card.footerText,
                fontSize = 12.sp,
                color = SubtitleMuted
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.clickable { onViewDetails() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "View Details",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryPurple
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    contentDescription = null,
                    tint = PrimaryPurple,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}

@Composable
private fun PatternDisclaimerCard() {
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
                painter = painterResource(id = R.drawable.ic_clipboard_info),
                contentDescription = null,
                tint = PrimaryPurple,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Categories describe recorded patterns, not the presence, absence, or severity of a medical condition.",
                fontSize = 11.sp,
                color = PrimaryPurple,
                lineHeight = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun PatternBottomNavigationBar(
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
            PatternNavItem(
                label = "Home",
                iconRes = R.drawable.ic_home_tab,
                isSelected = selectedTab == "Home",
                onClick = { onTabSelected("Home") }
            )
            PatternNavItem(
                label = "Track",
                iconRes = R.drawable.ic_calendar,
                isSelected = selectedTab == "Track",
                onClick = { onTabSelected("Track") }
            )
            PatternNavItem(
                label = "Insights",
                iconRes = R.drawable.ic_sparkles,
                isSelected = selectedTab == "Insights",
                onClick = { onTabSelected("Insights") }
            )
            PatternNavItem(
                label = "Nutrition",
                iconRes = R.drawable.ic_leaf,
                isSelected = selectedTab == "Nutrition",
                onClick = { onTabSelected("Nutrition") }
            )
            PatternNavItem(
                label = "Progress",
                iconRes = R.drawable.ic_progress_tab,
                isSelected = selectedTab == "Progress",
                onClick = { onTabSelected("Progress") }
            )
        }
    }
}

@Composable
private fun PatternNavItem(
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
fun PatternDetectionScreenPreview() {
    PatternDetectionScreen()
}
