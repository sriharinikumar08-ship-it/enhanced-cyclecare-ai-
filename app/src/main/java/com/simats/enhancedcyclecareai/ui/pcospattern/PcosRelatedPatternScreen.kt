package com.simats.enhancedcyclecareai.ui.pcospattern

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
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.simats.enhancedcyclecareai.R
import com.simats.enhancedcyclecareai.ui.theme.BadgeBackground
import com.simats.enhancedcyclecareai.ui.theme.FieldBorder
import com.simats.enhancedcyclecareai.ui.theme.PrimaryPurple
import com.simats.enhancedcyclecareai.ui.theme.PrivacyCardBg
import com.simats.enhancedcyclecareai.ui.theme.SplashBackground
import com.simats.enhancedcyclecareai.ui.theme.SubtitleMuted
import com.simats.enhancedcyclecareai.ui.theme.TitleDark

@Composable
fun PcosRelatedPatternScreen(
    modifier: Modifier = Modifier,
    viewModel: PcosRelatedPatternViewModel = viewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
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
            PcosHeader(
                onBackClick = {
                    viewModel.onBackClicked()
                    onNavigateBack()
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Badge Pill
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(BadgeBackground)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = uiState.badgeText,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = PrimaryPurple
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 3. Warning Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFFEF3C7))
                    .border(1.dp, Color(0xFFFDE68A), RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_warning),
                        contentDescription = null,
                        tint = Color(0xFFB45309),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = uiState.warningText,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF92400E),
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 4. "What does this mean?" Card
            InfoCard(
                title = uiState.whatDoesThisMeanTitle,
                body = uiState.whatDoesThisMeanBody
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 5. "Recorded indicators" Card
            RecordedIndicatorsCard(
                title = uiState.recordedIndicatorsTitle
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 6. "Supporting history" Card
            SupportingHistoryCard(
                title = uiState.supportingHistoryTitle
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 7. "How certain is this explanation?" Card
            ConfidenceExplanationCard(
                title = uiState.certaintyTitle,
                confidenceBadge = uiState.confidenceBadge,
                description = uiState.confidenceDescription
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 8. "What you can do" Card
            InfoCard(
                title = uiState.actionToDoTitle,
                body = uiState.actionToDoBody
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 9. Bottom Action Buttons
            Button(
                onClick = { viewModel.onContinueTrackingClicked() },
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
                        painter = painterResource(id = R.drawable.ic_calendar),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Continue Tracking",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = { viewModel.onSeekProfessionalAdviceClicked() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, FieldBorder),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = PrimaryPurple,
                    containerColor = Color.White
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_doctor),
                        contentDescription = null,
                        tint = PrimaryPurple,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Seek Professional Advice",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun PcosHeader(
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
                text = "UNDERSTAND • WHY THIS APPEARED",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryPurple,
                letterSpacing = 1.sp
            )
            Text(
                text = "PCOS-related patterns",
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
private fun InfoCard(
    title: String,
    body: String
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
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TitleDark
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = body,
                fontSize = 13.sp,
                color = TitleDark,
                lineHeight = 19.sp
            )
        }
    }
}

@Composable
private fun RecordedIndicatorsCard(
    title: String
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
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TitleDark
            )
            Spacer(modifier = Modifier.height(14.dp))

            IndicatorRow(
                iconRes = R.drawable.ic_calendar,
                titleText = "Cycle lengths vary by 10 days",
                subText = "Shortest 28 days · longest 38 days"
            )

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = FieldBorder, thickness = 0.5.dp)
            Spacer(modifier = Modifier.height(12.dp))

            IndicatorRow(
                iconRes = R.drawable.ic_clock_alert,
                titleText = "One longer cycle in your history",
                subText = "31 Jul - 6 Sep - 38 days"
            )

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = FieldBorder, thickness = 0.5.dp)
            Spacer(modifier = Modifier.height(12.dp))

            IndicatorRow(
                iconRes = R.drawable.ic_fatigue,
                titleText = "Fatigue was recorded",
                subText = "A non-specific symptom; not evidence of PCOS."
            )
        }
    }
}

@Composable
private fun IndicatorRow(
    iconRes: Int,
    titleText: String,
    subText: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
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
                text = titleText,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = TitleDark
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subText,
                fontSize = 12.sp,
                color = SubtitleMuted
            )
        }
    }
}

@Composable
private fun SupportingHistoryCard(
    title: String
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
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TitleDark
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "6 completed cycles · dates are period starts",
                fontSize = 13.sp,
                color = SubtitleMuted
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Recent start dates",
                    fontSize = 13.sp,
                    color = SubtitleMuted
                )
                Text(
                    text = "31 Jul · 7 Sep · 5 Oct",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = TitleDark
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Recorded lengths: 30, 30, 32, 29, 38 and 28 days. No conclusion can be drawn from one longer cycle alone.",
                fontSize = 13.sp,
                color = TitleDark,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
private fun ConfidenceExplanationCard(
    title: String,
    confidenceBadge: String,
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TitleDark
                )

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(BadgeBackground)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = confidenceBadge,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = PrimaryPurple
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Confidence segmented bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                repeat(6) { index ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(if (index == 0) PrimaryPurple else Color(0xFFE8DEF2))
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Limited",
                    fontSize = 11.sp,
                    color = SubtitleMuted
                )
                Text(
                    text = "More context",
                    fontSize = 11.sp,
                    color = SubtitleMuted
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = description,
                fontSize = 12.sp,
                color = SubtitleMuted,
                lineHeight = 17.sp
            )
        }
    }
}
