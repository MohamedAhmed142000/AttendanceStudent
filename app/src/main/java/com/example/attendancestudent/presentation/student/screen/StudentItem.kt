package com.example.attendancestudent.presentation.student.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
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
    onClearSessions: () -> Unit // ✅ الزر الجديد
) {
    var showDialog by remember { mutableStateOf(false) }
    val cardColor = when {
        student.attendedSessions >= 10 -> Color(0xFFFFF3E0) // برتقالي
        student.attendedSessions >= 5 -> Color(0xFFE3F2FD)  // أزرق فاتح
        else -> Color(0xFFE8F5E9)                          // أخضر فاتح
    }

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
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)

    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp, end = 30.dp),
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "👤 الاسم: ${student.fullName}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "📘 السنة الدراسية: ${student.academicYear}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "📅 عدد الحصص: ${student.attendedSessions}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text("💰 التكلفة: $cost جنيه", style = MaterialTheme.typography.bodySmall)

                Text(
                    text = "📚 المادة: ${student.subject}",
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Button(onClick = onMarkAttendance) {
                        Text("تسجيل حضور ✅")
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Button(onClick = onClearSessions) {
                        Text("تم الدفع") // ✅ زر التصفير
                    }
                }
            }
            Column {
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
