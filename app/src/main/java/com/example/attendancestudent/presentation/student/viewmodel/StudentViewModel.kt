package com.example.attendancestudent.presentation.student.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attendancestudent.domain.model.Student
import com.example.attendancestudent.domain.usecase.student.StudentUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class StudentViewModel(
    private val useCases: StudentUseCases
) : ViewModel() {

    private val _students = MutableStateFlow<List<Student>>(emptyList())
    val students: StateFlow<List<Student>> = _students.asStateFlow()

    private val _yearPrices = MutableStateFlow<Map<String, Int>>(emptyMap())
    val yearPrices = _yearPrices.asStateFlow()

    private val _attendanceResult = MutableStateFlow<Boolean?>(null)
    val attendanceResult: StateFlow<Boolean?> = _attendanceResult

    private val _searchResult = MutableStateFlow<List<Student>>(emptyList())
    val searchResult: StateFlow<List<Student>> = _searchResult

    private val allStudentsFlow = useCases.getAllStudents()


    init {
        viewModelScope.launch {
            useCases.getAllPrices().collect { list ->
                _yearPrices.value = list.associate { it.year to it.pricePerSession }
            }
        }
        getAllStudents()
    }


     fun getAllStudents() {
        viewModelScope.launch {
            useCases.getAllStudents()
                .collect { list ->
                    _students.value = list
                }
        }
    }

    fun addStudent(student: Student) {
        viewModelScope.launch {
            useCases.insertStudent(student)
        }
    }


    fun updateYearPrice(year: String, price: Int) {
        viewModelScope.launch {
            useCases.updatePrice(year, price)
        }
    }

    fun searchStudentByName(name: String) {
        viewModelScope.launch {
            useCases.searchStudents(name).collect {
                _searchResult.value = it
            }
        }
    }

    fun calculateCost(student: Student): Int {
        val price = yearPrices.value[student.academicYear] ?: 0
        return student.attendedSessions * price
    }


    fun resetSessions(studentId: Int) {
        viewModelScope.launch {
            useCases.resetSessions(studentId)
        }
    }

    fun incrementStudentSessions(studentId: Int) {
        viewModelScope.launch {
            val result = useCases.incrementSession(studentId)
            _attendanceResult.value = result
        }
    }

    fun clearAttendanceResult() {
        _attendanceResult.value = null
    }

    fun loadStudentsBySubject(subject: String) {
        viewModelScope.launch {
            useCases.getStudentsBySubject(subject).collect {
                _students.value = it
            }
        }
    }

    fun loadStudentsByYearAndSubject(year: String, subject: String) {
        viewModelScope.launch {
            useCases.getStudentsByYearAndSubject(year, subject).collect {
                _students.value = it
            }
        }
    }
    fun getStudentsByYear(year: String) {
        viewModelScope.launch {
            useCases.getStudentsByYear(year).collect { students ->
                _students.value = students
            }
        }
    }

    fun deleteStudent(student: Student) {
        viewModelScope.launch {
            useCases.deleteStudent(student)
        }
    }
}