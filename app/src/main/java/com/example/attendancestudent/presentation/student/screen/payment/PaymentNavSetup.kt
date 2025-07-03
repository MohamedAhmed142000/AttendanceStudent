package com.example.attendancestudent.presentation.student.screen.payment

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.attendancestudent.presentation.student.screen.StudentScreen
import com.example.attendancestudent.presentation.student.screen.splash.SplashScreen
import com.example.attendancestudent.presentation.student.viewmodel.StudentViewModel

@Composable
fun AppNavHost(navController: NavHostController, viewModel: StudentViewModel) {
    NavHost(
        navController = navController,
        startDestination = "splash" // ✅ Start from Splash
    ) {
        // ✅ Splash Route
        composable("splash") {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate("students") {
                        popUpTo("splash") { inclusive = true } // remove splash from back stack
                    }
                }
            )
        }
        composable("students") {
            StudentScreen(
                viewModel = viewModel, onPayClicked = { student ->
                    navController.navigate("payment/${student.id}")
                })
        }


        composable("payment/{studentId}") { backStackEntry ->
            val studentId = backStackEntry.arguments?.getString("studentId")?.toIntOrNull()

            if (studentId != null) {
                PaymentWrapperScreen(
                    studentId = studentId,
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() })
            } else {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("❌ خطأ في تحميل الطالب")
                }
            }
        }
    }
}