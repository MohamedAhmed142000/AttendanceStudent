package com.example.attendancestudent.presentation.student.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.attendancestudent.domain.model.Student

@Composable
fun StudentItem(
    student: Student,
    onMarkAttendance: () -> Unit,
    cost: Int,
    onEditStudent: (Student) -> Unit,
    onDeleteConfirmed: (Student) -> Unit,
    onPayClicked: (Student) -> Unit,
    onMarkAbsent: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    val cardColor = when {
        student.attendedSessions >= 10 -> Color(0xFFFFF3E0) // برتقالي
        student.attendedSessions >= 5 -> Color(0xFFE3F2FD)  // أزرق فاتح
        else -> Color(0xFFE8F5E9)                          // أخضر فاتح
    }
//todo implement delete student
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("تأكيد الحذف") },
            text = { Text("هل أنت متأكد أنك تريد حذف ${student.fullName}؟") },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        onDeleteConfirmed(student)
                    }) {
                    Text("نعم")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showDialog = false }) {
                    Text("إلغاء")
                }
            })
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, end = 30.dp),
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("👤 الاسم: ${student.fullName}", style = MaterialTheme.typography.titleMedium)
                Text(
                    "📘 السنة الدراسية: ${student.academicYear}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text("📚 المادة: ${student.subject}", style = MaterialTheme.typography.bodySmall)
                Text(
                    "📅 عدد الحصص: ${student.attendedSessions}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    "💵 المدفوعة: ${student.paidSessions}",
                    style = MaterialTheme.typography.bodySmall
                )
                // Text("💰 التكلفة: $cost جنيه", style = MaterialTheme.typography.bodySmall)
                //   Text("🚫 الغياب: ${student.absentCount}")


                Spacer(modifier = Modifier.width(8.dp))
                //todo implement mark attendance , mark absent and pay
                Row {
                    Button(onClick = onMarkAttendance) {
                        Text("تسجيل حضور ✅")
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    Button(onClick = { onPayClicked(student) }) {
                        Text(" الدفع")
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Column {


                        Spacer(modifier = Modifier.height(4.dp))

                        Button(
                            onClick = onMarkAbsent,
                            enabled = student.attendedSessions == 0 && !student.isAbsent
                        ) {
                            Text("غياب")
                        }
                        if (student.isAbsent) {
                            Text(
                                "🚫 متغيب",
                                color = Color.Red,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }

                }


            }

//todo implement buttom edit and delete
            Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.End) {

                    IconButton(onClick = { showDialog = true }) { // ✅ زر الإكس
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "حذف الطالب",
                            tint = Color.Red
                        )
                    }
                    IconButton(onClick = { onEditStudent(student) }) {
                        Icon(Icons.Default.Edit, contentDescription = "تعديل", tint = Color.Blue)
                    }


            }
        }
    }
}
