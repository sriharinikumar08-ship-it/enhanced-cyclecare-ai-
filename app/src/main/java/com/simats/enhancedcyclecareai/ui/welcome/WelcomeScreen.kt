package com.simats.enhancedcyclecareai.ui.welcome

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.simats.enhancedcyclecareai.R
import com.simats.enhancedcyclecareai.ui.theme.BadgeBackground
import com.simats.enhancedcyclecareai.ui.theme.BadgeText
import com.simats.enhancedcyclecareai.ui.theme.IllustrationCardBg
import com.simats.enhancedcyclecareai.ui.theme.PrimaryPurple
import com.simats.enhancedcyclecareai.ui.theme.SplashBackground
import com.simats.enhancedcyclecareai.ui.theme.SubtitleMuted
import com.simats.enhancedcyclecareai.ui.theme.TaglineMuted
import com.simats.enhancedcyclecareai.ui.theme.TealAccent
import com.simats.enhancedcyclecareai.ui.theme.TitleDark

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    viewModel: WelcomeViewModel = viewModel(),
    onGetStartedClick: () -> Unit = {},
    onLoginClick: () -> Unit = {}
) {
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // 1. Top Bar Header
            WelcomeHeader()

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Illustration Section
            WelcomeIllustrationCard()

            Spacer(modifier = Modifier.height(20.dp))

            // 3. Headline & Subtitle
            WelcomeTextSection()

            Spacer(modifier = Modifier.height(20.dp))

            // 4. Feature Row
            WelcomeFeatureRow()

            Spacer(modifier = Modifier.height(24.dp))

            // 5. Action Buttons
            WelcomeActionButtons(
                onGetStarted = {
                    viewModel.onGetStartedClicked()
                    onGetStartedClick()
                },
                onLogin = {
                    viewModel.onLoginClicked()
                    onLoginClick()
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 6. Bottom Disclaimer
            Text(
                text = "Your body, your pace. Guidance for awareness, not diagnosis.",
                fontSize = 11.sp,
                color = TaglineMuted,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}

@Composable
private fun WelcomeHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "CycleCare AI",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TitleDark
        )

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(BadgeBackground)
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = "Made for your everyday",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = BadgeText
            )
        }
    }
}

@Composable
private fun WelcomeIllustrationCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(210.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(IllustrationCardBg),
        contentAlignment = Alignment.Center
    ) {
        // Main Illustration
        Image(
            painter = painterResource(id = R.drawable.ic_welcome_illustration),
            contentDescription = "Welcome Illustration",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )

        // Floating Badge Left ("Your cycle")
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 16.dp, top = 24.dp)
                .shadow(4.dp, shape = CircleShape)
                .clip(CircleShape)
                .background(Color.White)
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_calendar),
                    contentDescription = null,
                    tint = PrimaryPurple,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Your cycle",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = PrimaryPurple
                )
            }
        }

        // Floating Badge Right ("Eat well")
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 32.dp)
                .shadow(4.dp, shape = CircleShape)
                .clip(CircleShape)
                .background(Color.White)
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_leaf),
                    contentDescription = null,
                    tint = TealAccent,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Eat well",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = TealAccent
                )
            }
        }
    }
}

@Composable
private fun WelcomeTextSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Understand Your Cycle.\nTrack Your Health. Eat\nBetter.",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = TitleDark,
            lineHeight = 32.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Track your cycle and symptoms, notice patterns with AI, and explore nutrition personalized to your preferences.",
            fontSize = 14.sp,
            color = SubtitleMuted,
            lineHeight = 20.sp
        )
    }
}

@Composable
private fun WelcomeFeatureRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FeatureItem(
            iconRes = R.drawable.ic_calendar,
            label = "Track gently",
            tint = PrimaryPurple
        )
        FeatureItem(
            iconRes = R.drawable.ic_sparkles,
            label = "Understand",
            tint = PrimaryPurple
        )
        FeatureItem(
            iconRes = R.drawable.ic_leaf,
            label = "Nourish",
            tint = TealAccent
        )
    }
}

@Composable
private fun FeatureItem(
    iconRes: Int,
    label: String,
    tint: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            tint = tint,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = SubtitleMuted
        )
    }
}

@Composable
private fun WelcomeActionButtons(
    onGetStarted: () -> Unit,
    onLogin: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Primary Button: Get Started
        Button(
            onClick = onGetStarted,
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
                    text = "Get Started",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // Secondary Button: Login
        OutlinedButton(
            onClick = onLogin,
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
                text = "Login",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryPurple
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen()
}
