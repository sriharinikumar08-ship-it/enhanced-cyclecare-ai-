package com.simats.enhancedcyclecareai.ui.report

import android.widget.Toast
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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
fun HealthReportScreen(
    modifier: Modifier = Modifier,
    viewModel: HealthReportViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    onNavigateTab: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedTab by viewModel.selectedTab.collectAsState()
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is HealthReportUiEvent.NavigateBack -> onNavigateBack()
                is HealthReportUiEvent.ShowToast -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                is HealthReportUiEvent.ExportPdf -> {}
                is HealthReportUiEvent.ShareReport -> {}
                is HealthReportUiEvent.PrintSave -> {}
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
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(12.dp))

                // 1. Header Bar
                ReportHeaderBar(onBackClick = { viewModel.onBackClicked() })

                Spacer(modifier = Modifier.height(12.dp))

                // 2. Patient Profile & Metadata Card
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
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(BadgeBackground),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_clipboard_info),
                                    contentDescription = null,
                                    tint = PrimaryPurple,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = uiState.brandTitle,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryPurple,
                                    letterSpacing = 1.sp
                                )
                                Text(
                                    text = uiState.reportHeader,
                                    fontSize = 10.sp,
                                    color = SubtitleMuted
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        HorizontalDivider(color = FieldBorder, thickness = 0.5.dp)
                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = uiState.patientName,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = TitleDark
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        ReportKeyValueRow("Age", uiState.patientAge)
                        ReportKeyValueRow("Prepared", uiState.preparedDate)
                        ReportKeyValueRow("Daily summary", uiState.dataSummary)
                        ReportKeyValueRow("Cycle context", uiState.cycleContext)

                        Spacer(modifier = Modifier.height(12.dp))

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(BadgeBackground)
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Text(
                                text = uiState.encryptionTag,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = PrimaryPurple
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 3. Cycle History Card
                ReportSectionCard(title = "Cycle history") {
                    uiState.cycleHistoryItems.forEachIndexed { index, (key, value) ->
                        if (index > 0) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        ReportKeyValueRow(key, value)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 4. Symptoms & Lifestyle Card
                ReportSectionCard(title = "Symptoms & lifestyle") {
                    uiState.symptomLifestyleItems.forEachIndexed { index, (key, value) ->
                        if (index > 0) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        ReportKeyValueRow(key, value)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = uiState.symptomLifestyleFooter,
                        fontSize = 11.sp,
                        color = SubtitleMuted
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 5. Risk-Pattern Awareness Categories Card
                ReportSectionCard(title = "Risk-pattern awareness categories") {
                    uiState.riskPatterns.forEachIndexed { index, (category, status, colorType) ->
                        if (index > 0) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = category, fontSize = 13.sp, color = TitleDark, fontWeight = FontWeight.Medium)
                            val badgeBg = when (colorType) {
                                ColorType.PURPLE -> BadgeBackground
                                ColorType.ORANGE -> Color(0xFFFEF3C7)
                                ColorType.GREEN -> Color(0xFFEAF3ED)
                            }
                            val badgeColor = when (colorType) {
                                ColorType.PURPLE -> PrimaryPurple
                                ColorType.ORANGE -> Color(0xFFB45309)
                                ColorType.GREEN -> Color(0xFF2E7D32)
                            }
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(badgeBg)
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(text = status, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = badgeColor)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = uiState.riskPatternFooter,
                        fontSize = 11.sp,
                        color = SubtitleMuted,
                        lineHeight = 15.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 6. Nutrition Guidance & Meal Activity Card
                ReportSectionCard(title = "Nutrition guidance & meal activity") {
                    uiState.nutritionItems.forEachIndexed { index, (key, value) ->
                        if (index > 0) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        ReportKeyValueRow(key, value)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = uiState.nutritionFooter,
                        fontSize = 11.sp,
                        color = SubtitleMuted,
                        lineHeight = 15.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 7. Tracking Progress & Next Steps Card
                ReportSectionCard(title = "Tracking progress & next steps") {
                    uiState.progressItems.forEachIndexed { index, (key, value) ->
                        if (index > 0) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        ReportKeyValueRow(key, value)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = uiState.progressRecommendation,
                        fontSize = 12.sp,
                        color = TitleDark,
                        lineHeight = 17.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 8. Privacy Notice Card (Light purple)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(PrivacyCardBg)
                        .border(1.dp, Color(0xFFE8DEF2), RoundedCornerShape(14.dp))
                        .padding(14.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_heart_privacy),
                            contentDescription = null,
                            tint = PrimaryPurple,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = uiState.privacyNotice,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = PrimaryPurple,
                            lineHeight = 16.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // 9. Export PDF Button
                Button(
                    onClick = { viewModel.onExportPdfClicked() },
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
                            painter = painterResource(id = R.drawable.ic_clipboard_info),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Export PDF",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // 10. Secondary Actions Row (Share & Print/Save)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = { viewModel.onShareReportClicked() },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(painter = painterResource(id = R.drawable.ic_arrow_right), contentDescription = null, tint = TitleDark, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "Share Report", fontSize = 13.sp, color = TitleDark)
                        }
                    }

                    OutlinedButton(
                        onClick = { viewModel.onPrintSaveClicked() },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(painter = painterResource(id = R.drawable.ic_check), contentDescription = null, tint = TitleDark, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "Print/Save", fontSize = 13.sp, color = TitleDark)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 11. Final Disclaimer
                Text(
                    text = uiState.finalDisclaimer,
                    fontSize = 11.sp,
                    color = SubtitleMuted,
                    lineHeight = 15.sp,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        // 12. Bottom Navigation Bar
        HealthReportBottomNavigationBar(
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
private fun ReportHeaderBar(onBackClick: () -> Unit) {
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

        Text(
            text = "Health Report",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TitleDark
        )

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
private fun ReportSectionCard(
    title: String,
    content: @Composable () -> Unit
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
            content()
        }
    }
}

@Composable
private fun ReportKeyValueRow(key: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = key,
            fontSize = 13.sp,
            color = SubtitleMuted,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = value,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = TitleDark,
            modifier = Modifier.weight(1.5f)
        )
    }
}

@Composable
private fun HealthReportBottomNavigationBar(
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
            ReportNavItem(
                label = "Home",
                iconRes = R.drawable.ic_home_tab,
                isSelected = selectedTab == "Home",
                onClick = { onTabSelected("Home") }
            )
            ReportNavItem(
                label = "Track",
                iconRes = R.drawable.ic_calendar,
                isSelected = selectedTab == "Track",
                onClick = { onTabSelected("Track") }
            )
            ReportNavItem(
                label = "Insights",
                iconRes = R.drawable.ic_sparkles,
                isSelected = selectedTab == "Insights",
                onClick = { onTabSelected("Insights") }
            )
            ReportNavItem(
                label = "Nutrition",
                iconRes = R.drawable.ic_leaf,
                isSelected = selectedTab == "Nutrition",
                onClick = { onTabSelected("Nutrition") }
            )
            ReportNavItem(
                label = "Progress",
                iconRes = R.drawable.ic_progress_tab,
                isSelected = selectedTab == "Progress",
                onClick = { onTabSelected("Progress") }
            )
        }
    }
}

@Composable
private fun ReportNavItem(
    label: String,
    iconRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            tint = if (isSelected) PrimaryPurple else SubtitleMuted,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) PrimaryPurple else SubtitleMuted
        )
    }
}
