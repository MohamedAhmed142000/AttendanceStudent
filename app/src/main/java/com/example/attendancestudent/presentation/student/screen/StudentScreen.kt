package com.example.attendancestudent.presentation.student.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.attendancestudent.R
import com.example.attendancestudent.domain.model.Student
import com.example.attendancestudent.presentation.student.viewmodel.StudentViewModel


@Composable
fun StudentScreen(viewModel: StudentViewModel) {
    val students by viewModel.students.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    val yearPrices by viewModel.yearPrices.collectAsState()
    var editingStudent by remember { mutableStateOf<Student?>(null) }
    val attendanceResult by viewModel.attendanceResult.collectAsState()
    var query by remember { mutableStateOf("") }
    val context = LocalContext.current // ✅ مهم للتوست
    val displayedStudents = if (query.isBlank()) students else {
        viewModel.searchResult.collectAsState().value
    }
    var confirmResetStudent by remember { mutableStateOf<Student?>(null) }
    val academicYears = listOf(
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

    var selectedYear by remember { mutableStateOf("") }
    var selectedSubject by remember { mutableStateOf("") }

    var yearExpanded by remember { mutableStateOf(false) }
    var subjectExpanded by remember { mutableStateOf(false) }


    LaunchedEffect(attendanceResult) {
        attendanceResult?.let {
            if (!it) {
                Toast.makeText(context, "تم تسجيل الحضور بالفعل اليوم", Toast.LENGTH_SHORT).show()
            }
            // ممكن ترجّعها null تاني لو حبيت تمسح القيمة
            viewModel.clearAttendanceResult() // ✅ Reset القيمة بعد التوست
        }
    }
    Scaffold(topBar = {
        TopBarApp()
    }, floatingActionButton = {
        FloatingActionButton(onClick = { showDialog = true }) {
            Icon(Icons.Default.Add, contentDescription = "Add Student")
        }
    }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            //todo implement search
            OutlinedTextField(
                value = query,
                onValueChange = {
                    query = it
                    viewModel.searchStudentByName(query)
                },
                label = { Text("ابحث باسم الطالب") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            //todo implement filter for year and subject
            Row(modifier = Modifier.padding(horizontal = 8.dp)) {
                // فلتر السنة
                Box(modifier = Modifier.weight(1f)) {
                    OutlinedTextField(
                        value = selectedYear,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("الصف الدراسي") },
                        trailingIcon = {
                            Row {
                                if (selectedYear.isNotBlank()) {
                                    IconButton(onClick = {
                                        selectedYear = ""
                                        // إعادة تحميل الكل
                                        viewModel.getAllStudents()
                                    }) {
                                        Icon(
                                            Icons.Default.Close,
                                            contentDescription = "إزالة الفلتر"
                                        )
                                    }
                                } else {
                                    IconButton(onClick = { yearExpanded = true }) {
                                        Icon(
                                            Icons.Default.ArrowDropDown,
                                            contentDescription = "اختر الصف"
                                        )
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    DropdownMenu(
                        expanded = yearExpanded,
                        onDismissRequest = { yearExpanded = false }) {
                        academicYears.forEach { year ->
                            DropdownMenuItem(
                                text = { Text(year) },
                                onClick = {
                                    selectedYear = year
                                    yearExpanded = false
                                    when {
                                        selectedSubject.isNotBlank() -> viewModel.loadStudentsByYearAndSubject(
                                            selectedYear,
                                            selectedSubject
                                        )

                                        else -> viewModel.getStudentsByYear(selectedYear)
                                    }
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(4.dp))
                // فلتر المادة
                Box(modifier = Modifier.weight(1f)) {
                    OutlinedTextField(
                        value = selectedSubject,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("المادة") },
                        trailingIcon = {
                            Row {
                                if (selectedSubject.isNotBlank()) {
                                    IconButton(onClick = {
                                        selectedSubject = ""
                                        // إعادة تحميل الكل
                                        viewModel.getAllStudents()
                                    }) {
                                        Icon(
                                            Icons.Default.Close,
                                            contentDescription = "إزالة الفلتر"
                                        )
                                    }
                                } else {
                                    IconButton(onClick = { subjectExpanded = true }) {
                                        Icon(
                                            Icons.Default.ArrowDropDown,
                                            contentDescription = "اختر المادة"
                                        )
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    DropdownMenu(
                        expanded = subjectExpanded,
                        onDismissRequest = { subjectExpanded = false }) {
                        subjects.forEach { subject ->
                            DropdownMenuItem(
                                text = { Text(subject) },
                                onClick = {
                                    selectedSubject = subject
                                    subjectExpanded = false
                                    when {
                                        selectedYear.isNotBlank() -> viewModel.loadStudentsByYearAndSubject(
                                            selectedYear,
                                            selectedSubject
                                        )

                                        else -> viewModel.loadStudentsBySubject(selectedSubject)
                                    }
                                }
                            )
                        }
                    }
                }
            }

//todo implement students list
            LazyColumn {
                items(displayedStudents) { student ->
                    StudentItem(
                        student = student,
                        cost = viewModel.calculateCost(student),
                        onMarkAttendance = { viewModel.incrementStudentSessions(student.id) },
                        onEditStudent = { editingStudent = it },
                        onDeleteConfirmed = { viewModel.deleteStudent(it) },
                        onClearSessions = {
                            confirmResetStudent = student
                        }

                    )
                }
            }
//todo implement add student
            if (showDialog) {
                AddStudentDialog(
                    onDismiss = { showDialog = false },
                    existingPrices = viewModel.yearPrices.collectAsState().value,
                    onAdd = { name, year, subject, price ->
                        // لو السنة مش موجودة وسعر اتبعت، سجّله
                        if (!yearPrices.containsKey(
                                Pair(
                                    selectedYear,
                                    selectedSubject
                                )
                            ) && price != null
                        ) {
                            viewModel.updateYearPrice(year, subject, price.toInt())
                        }

                        // أضف الطالب
                        viewModel.addStudent(
                            Student(
                                fullName = name,
                                academicYear = year,
                                subject = subject
                            )
                        )
                        showDialog = false
                    })
            }

//todo implement edit student
            if (editingStudent != null) {
                EditStudentDialog(
                    student = editingStudent!!,
                    yearPrices = viewModel.yearPrices.collectAsState().value,
                    onDismiss = { editingStudent = null },
                    onUpdate = { updatedStudent, year, subject, newPrice ->
                        if (newPrice != null) {
                            viewModel.updateYearPrice(year, subject, newPrice)
                        }
                        viewModel.addStudent(updatedStudent) // أو اعمل updateStudent لو عندك ميثود ليها
                        editingStudent = null
                    })
            }
//todo implement reset sessions
            if (confirmResetStudent != null) {
                androidx.compose.material3.AlertDialog(
                    onDismissRequest = { confirmResetStudent = null },
                    title = { Text("تأكيد الدفع") },
                    text = {
                        Text("هل أنت متأكد أنك تريد تصفير عدد الحصص للطالب ${confirmResetStudent?.fullName}؟")
                    },
                    confirmButton = {
                        androidx.compose.material3.TextButton(
                            onClick = {
                                viewModel.resetSessions(confirmResetStudent!!.id)
                                confirmResetStudent = null
                            }
                        ) {
                            Text("نعم")
                        }
                    },
                    dismissButton = {
                        androidx.compose.material3.TextButton(
                            onClick = { confirmResetStudent = null }
                        ) {
                            Text("إلغاء")
                        }
                    }
                )
            }
        }
    }
}


