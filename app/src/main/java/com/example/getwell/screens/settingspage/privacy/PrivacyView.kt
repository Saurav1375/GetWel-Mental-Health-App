package com.example.getwell.screens.settingspage.privacy


import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.getwell.screens.customFont

@Composable
fun PrivacyView() {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val email = "getwelplus@gmail.com"
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 4.dp, end = 4.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "Privacy and Policy\n",
            style = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = Color.White
            )
        )

        Text(
            text = "1)Information We Collect\n" +
                    "We collect personal information (e.g., name, email) and health data (e.g., mood tracking) to personalize your experience and improve the app.\n" +
                    "\n" +
                    "2)Use of Information\n" +
                    "Your information is used to provide tailored resources, enhance functionality, and support community engagement.\n" +
                    "\n" +
                    "3)Data Sharing\n" +
                    "We do not sell your data. We may share information with professionals for content validation or as required by law.\n" +
                    "\n" +
                    "4)Security\n" +
                    "We implement measures to protect your data from unauthorized access.\n" +
                    "\n" +
                    "5)Chatroom\n" +
                    "We offer chatroom features that allow you to connect with other users, fostering community support and engagement. Spamming in the chatroom and use of malicious language is strictly prohibited\n" +
                    "\n" +
                    "6)Changes\n" +
                    "We may update this policy and will notify you of significant changes.\n" +
                    "\n" +
                    "7)Contact Us\n" +
                    "For questions, contact us at",
            style = TextStyle(
                fontFamily = customFont,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color.White
            )

        )
        Text(
            modifier = Modifier.clickable {

                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:$email")
                }
                context.startActivity(intent)


            },
            text = email,
            style = TextStyle(
                fontFamily = customFont,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color.Cyan
            )

        )
        Spacer(modifier = Modifier.height(32.dp))

    }
}