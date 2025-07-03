package com.example.attendancestudent.presentation.student.screen.payment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.attendancestudent.domain.model.Student

@Composable
fun PaymentScreen(
    student: Student,
    onConfirmPayment: (Int) -> Unit,
    onBack: () -> Unit,

    ) {
    var paidInput by remember { mutableStateOf("") }

    val remainingSessions = student.attendedSessions - student.paidSessions
    val remainingCost = remainingSessions * student.pricePerSession
    val cardColor = when {
        student.attendedSessions >= 10 -> Color(0xFFFFF3E0) // برتقالي
        student.attendedSessions >= 5 -> Color(0xFFE3F2FD)  // أزرق فاتح
        else -> Color(0xFFE8F5E9)                          // أخضر فاتح
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)


    ){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("اسم الطالب: ${student.fullName}", style = MaterialTheme.typography.titleMedium)
        Text("السنة الدراسية: ${student.academicYear}")
        Text("المادة: ${student.subject}")
        Text("عدد الحصص المحضورة: ${student.attendedSessions}")
        Text("عدد الحصص المدفوعة: ${student.paidSessions}")
        Text("سعر الحصة: ${student.pricePerSession} جنيه")
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = paidInput,
            onValueChange = { paidInput = it },
            label = { Text("عدد الحصص التي تم دفعها") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val newPaid = paidInput.toIntOrNull()
                if (newPaid != null && newPaid >= 0) {
                    onConfirmPayment(newPaid)
                }
            }, modifier = Modifier.fillMaxWidth()
        ) {
            Text("تأكيد الدفع")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("الحصص المتبقية: $remainingSessions")
        Text("المبلغ المتبقي: $remainingCost جنيه")

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedButton(
            onClick = onBack, modifier = Modifier.fillMaxWidth()
        ) {
            Text("رجوع")
        }
    }
}}
