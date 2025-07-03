package com.example.attendancestudent.presentation.student.screen.add_dialog

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.attendancestudent.R


@Composable
fun AddStudentDialog(
    onDismiss: () -> Unit,
    onAdd: (name: String, year: String, subject: String, price: Int?) -> Unit,
    existingPrices: Map<Pair<String, String>, Int>
) {
    var name by remember { mutableStateOf("") }
    var selectedYear by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var showPriceField by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var subject by remember { mutableStateOf("") }
    var selectedSubject by remember { mutableStateOf("") }

    val years = listOf(
        stringResource(R.string.First_grade),
        stringResource(R.string.Second_grade),
        stringResource(R.string.Third_grade),
        stringResource(R.string.Fourth_grade),
        stringResource(R.string.Fifth_grade),
        stringResource(R.string.Sixth_grade),
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
//todo implement add student
    AlertDialog(onDismissRequest = onDismiss, title = { Text("إضافة طالب جديد") }, text = {
        Column {
            OutlinedTextField(
                value = name, onValueChange = {
                name = it
                showError = false
            }, label = { Text("اسم الطالب") }, modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Dropdown لاختيار السنة الدراسية
            var expandedyear by remember { mutableStateOf(false) }



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
                })

            DropdownMenu(expanded = expandedyear, onDismissRequest = { expandedyear = false }) {
                years.forEach { year ->
                    DropdownMenuItem(text = { Text(year) }, onClick = {
                        selectedYear = year
                        expandedyear = false
                        showPriceField = !existingPrices.containsKey(Pair(year , selectedSubject))

                    })
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            var expandedsubject by remember { mutableStateOf(false) }
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
                })

            DropdownMenu(
                expanded = expandedsubject, onDismissRequest = { expandedsubject = false }) {
                subjects.forEach { subject ->
                    DropdownMenuItem(text = { Text(subject) }, onClick = {
                        selectedSubject = subject
                        expandedsubject = false
                        showPriceField = !existingPrices.containsKey(Pair(selectedYear, subject))
                    })
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
    }, confirmButton = {
        Button(onClick = {
            if (name.isBlank() || selectedYear.isBlank() || (showPriceField && price.isBlank())) {
                showError = true
            } else {
                onAdd(
                    name.trim(), selectedYear.trim(), selectedSubject.trim(), price.toIntOrNull()
                )
            }
        }) {
            Text("إضافة")
        }
    }, dismissButton = {
        OutlinedButton(onClick = onDismiss) {
            Text("إلغاء")
        }
    })
}
