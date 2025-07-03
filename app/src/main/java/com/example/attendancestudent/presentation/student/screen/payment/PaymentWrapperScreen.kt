package com.example.attendancestudent.presentation.student.screen.payment

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.attendancestudent.domain.model.Student
import com.example.attendancestudent.presentation.student.viewmodel.StudentViewModel

@Composable
fun PaymentWrapperScreen(
    studentId: Int,
    viewModel: StudentViewModel,
    onBack: () -> Unit
) {
    val studentState by produceState<Student?>(initialValue = null, studentId) {
        value = viewModel.getStudentById(studentId)
    }

    studentState?.let { student ->
        PaymentScreen(
            student = student,
            onConfirmPayment = { newPaid ->
                viewModel.updatePaidSessions(student.id, newPaid)
                viewModel.getAllStudents()
                onBack()
            },
            onBack = onBack
        )
    } ?: Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}
