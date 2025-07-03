package com.example.attendancestudent.presentation.student.screen.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(500L)
        onSplashFinished() // ✅ مهم جدًا
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "حضور الطلاب",
                style = MaterialTheme.typography.headlineLarge.copy(fontSize = 28.sp),
                color = Color(0xFF4CAF50) // أخضر أنيق
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "إدارة جلسات الطلاب والمدفوعات بسهولة",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
        }

        // اسمك في الأسفل
        Text(
            text = "Developed by Mohamed Ahmed",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 16.dp),
            style = MaterialTheme.typography.bodySmall,
            color = Color.DarkGray
        )
    }
}
