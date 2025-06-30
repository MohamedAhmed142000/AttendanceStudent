package com.example.attendancestudent.presentation.student.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.attendancestudent.domain.model.Student

@Composable
fun EditStudentDialog(
    student: Student,
    yearPrices: Map<String, Int>,
    onDismiss: () -> Unit,
    onUpdate: (Student, newYear: String, subject: String, newPrice: Int?) -> Unit
) {
    var name by remember { mutableStateOf(student.fullName) }
    var selectedYear by remember { mutableStateOf(student.academicYear) }
    val currentPrice = yearPrices[selectedYear]?.toString() ?: ""
    var price by remember { mutableStateOf(currentPrice) }
    val showPriceField = remember(selectedYear, yearPrices) {
        !yearPrices.containsKey(selectedYear)
    }
    var subject by remember { mutableStateOf("") }
    var selectedSubject by remember { mutableStateOf("") }



    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("تعديل بيانات الطالب") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("اسم الطالب") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Dropdown السنة الدراسية
                val years = listOf(
                    "الصف الأول الابتدائي",
                    "الصف الثاني الابتدائي",
                    "الصف الثالث الابتدائي",
                    "الصف الرابع الابتدائي",
                    "الصف الخامس الابتدائي",
                    "الصف السادس الابتدائي",
                    "الصف الأول الإعدادي",
                    "الصف الثاني الإعدادي",
                    "الصف الثالث الإعدادي",
                    "الصف الأول الثانوي",
                    "الصف الثاني الثانوي",
                    "الصف الثالث الثانوي",
                )
                val subjects = listOf(
                    "فيزياء",
                    "كيمياء",
                    "رياضيات",
                    "أحياء",
                    "عربي",
                    "تاريخ",
                    "جغرافيا",
                    "جيولوجيا",
                    "علوم",
                    "انجليزي",
                    "دراسات اجتماعيه",

                )
                var expandedyear by remember { mutableStateOf(false) }
                var expandedsubject by remember { mutableStateOf(false) }

                OutlinedTextField(
                    value = selectedYear,
                    onValueChange = {},
                    label = { Text("السنة الدراسية") },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { expandedyear = true }) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "اختر السنة")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                DropdownMenu(expanded = expandedyear, onDismissRequest = { expandedyear = false }) {
                    years.forEach { year ->
                        DropdownMenuItem(
                            text = { Text(year) },
                            onClick = {
                                selectedYear = year
                                expandedyear = false
                            }
                        )
                    }
                }
                OutlinedTextField(
                    value = selectedSubject,
                    onValueChange = {},
                    label = { Text("المادة") },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { expandedsubject = true }) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "اختر المادة")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                DropdownMenu(
                    expanded = expandedsubject,
                    onDismissRequest = { expandedsubject = false }) {
                    subjects.forEach { subject ->
                        DropdownMenuItem(
                            text = { Text(subject) },
                            onClick = {
                                selectedSubject = subject
                                expandedsubject = false
                            }
                        )
                    }
                }


                if (showPriceField) {
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = price,
                        onValueChange = { price = it },
                        label = { Text("سعر الحصة (لو السنة جديدة)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                onUpdate(
                    student.copy(
                        fullName = name,
                        academicYear = selectedYear,
                        subject = selectedSubject
                    ),
                    selectedYear,
                    selectedSubject,
                    price.toIntOrNull()
                )
            }) {
                Text("حفظ")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("إلغاء")
            }
        }
    )
}
