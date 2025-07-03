package com.example.attendancestudent.presentation.student.screen.edit_dialog

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.attendancestudent.R
import com.example.attendancestudent.domain.model.Student

@Composable
fun EditStudentDialog(
    student: Student,
    yearPrices:  Map<Pair<String, String>, Int>,
    onDismiss: () -> Unit,
    onUpdate: (Student, newYear: String, subject: String, newPrice: Int?) -> Unit
) {
    var name by remember { mutableStateOf(student.fullName) }
    var selectedYear by remember { mutableStateOf(student.academicYear) }
    var selectedSubject by remember { mutableStateOf(student.subject) }
    val currentPrice = yearPrices[Pair(selectedYear, selectedSubject)]?.toString() ?: ""
    var price by remember { mutableStateOf(currentPrice) }
    val showPriceField = remember(selectedYear,selectedSubject, yearPrices) {
        !yearPrices.containsKey(Pair(selectedYear,selectedSubject))
    }
    var subject by remember { mutableStateOf("") }



//todo implement edit student
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
                    stringResource(R.string.First_grade),
                    stringResource(R.string.Second_grade), stringResource(R.string.Third_grade),
                    stringResource(R.string.Fourth_grade),
                    stringResource(R.string.Fifth_grade), stringResource(R.string.Sixth_grade),
                    stringResource(R.string.First_year_of_middle_school),
                    stringResource(R.string.Second_year_of_middle_school),
                    stringResource(R.string.Thrid_year_of_middle_school),
                    stringResource(R.string.First_year_of_high_school),
                    stringResource(R.string.Second_year_of_high_school),
                    stringResource(R.string.Thrid_year_of_high_school)
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
//todo implement filter for year and subject
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
                        label = { Text("سعر الحصة (لو السنة او المادة  جديدة)") },
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
