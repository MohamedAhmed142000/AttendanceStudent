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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddStudentDialog(
    onDismiss: () -> Unit,
    onAdd: (name: String, year: String, subject: String,price: Int?) -> Unit,
    existingPrices: Map<String, Int>
) {
    var name by remember { mutableStateOf("") }
    var selectedYear by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var showPriceField by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var subject by remember { mutableStateOf("") }
    var selectedSubject by remember { mutableStateOf("") }

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

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("إضافة طالب جديد") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        showError = false
                    },
                    label = { Text("اسم الطالب") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Dropdown لاختيار السنة الدراسية
                var expandedyear by remember { mutableStateOf(false) }
                var expandedsubject by remember { mutableStateOf(false) }


                OutlinedTextField(
                    value = selectedYear,
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    label = { Text("السنة الدراسية") },
                    trailingIcon = {
                        IconButton(onClick = { expandedyear = true }) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "اختر السنة")
                        }
                    }
                )

                DropdownMenu(expanded = expandedyear, onDismissRequest = { expandedyear = false }) {
                    years.forEach { year ->
                        DropdownMenuItem(
                            text = { Text(year) },
                            onClick = {
                                selectedYear = year
                                expandedyear = false
                                showPriceField = !existingPrices.containsKey(year)

                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = selectedSubject,
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    label = { Text("المادة") },
                    trailingIcon = {
                        IconButton(onClick = { expandedsubject = true }) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "اختر المادة")
                        }
                    }
                )

                DropdownMenu(expanded = expandedsubject, onDismissRequest = { expandedsubject = false }) {
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

                Spacer(modifier = Modifier.height(12.dp))

                // حقل السعر يظهر فقط لو السنة غير موجودة
                if (showPriceField) {
                    OutlinedTextField(
                        value = price,
                        onValueChange = { price = it },
                        label = { Text("سعر الحصة (لو أول مرة)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                if (showError) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("يرجى تعبئة جميع الحقول المطلوبة", color = Color.Red)
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                if (name.isBlank() || selectedYear.isBlank() || (showPriceField && price.isBlank())) {
                    showError = true
                } else {
                    onAdd(name.trim(), selectedYear.trim(), subject.trim(),price.toIntOrNull())
                }
            }) {
                Text("إضافة")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("إلغاء")
            }
        }
    )
}
