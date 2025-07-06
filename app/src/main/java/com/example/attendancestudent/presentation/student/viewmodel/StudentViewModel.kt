package com.example.attendancestudent.presentation.student.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attendancestudent.domain.model.Student
import com.example.attendancestudent.domain.usecase.student.StudentUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class StudentViewModel(
    private val useCases: StudentUseCases
) : ViewModel() {

    private val _students = MutableStateFlow<List<Student>>(emptyList())
    val students: StateFlow<List<Student>> = _students.asStateFlow()

    private val _yearPrices = MutableStateFlow<Map<Pair<String, String>, Int>>(emptyMap())
    val yearPrices: StateFlow<Map<Pair<String, String>, Int>> = _yearPrices

    private val _attendanceResult = MutableStateFlow<Boolean?>(null)
    val attendanceResult: StateFlow<Boolean?> = _attendanceResult

    private val _searchResult = MutableStateFlow<List<Student>>(emptyList())
    val searchResult: StateFlow<List<Student>> = _searchResult

    private val allStudentsFlow = useCases.getAllStudents()


    init {
        viewModelScope.launch {
            useCases.getAllPrices().collect { list ->
                _yearPrices.value =
                    list.associate { Pair(it.year, it.subject) to it.pricePerSession }
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


    fun updateYearPrice(year: String, subject: String, price: Int) {
        viewModelScope.launch {
            useCases.updatePrice(year, subject, price)
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
        val key = Pair(student.academicYear, student.subject)
        val price = yearPrices.value[key] ?: 0
        return student.attendedSessions * price
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

    fun getStudentById(id: Int): Student? {
        var student: Student? = null
        runBlocking {
            student = useCases.getStudentById(id)

            student?.let {
                val price = _yearPrices.value[Pair(it.academicYear, it.subject)] ?: 0
                student = it.copy(pricePerSession = price.toDouble())
            }
        }

        return student
    }

    fun updatePaidSessions(studentId: Int, newPaidSessions: Int) {
        viewModelScope.launch {
            val student = useCases.getStudentById(studentId)
            if (student != null && student.paidSessions < student.attendedSessions) {
                val price = yearPrices.value[Pair(student.academicYear, student.subject)] ?: 0
                if (student.paidSessions >= student.attendedSessions) {
                    return@launch // وقف التنفيذ، مفيش دفع
                } else {
                    val newPaidSessions = student.paidSessions + 1

                    val updatedStudent = student.copy(
                        paidSessions = newPaidSessions,
                        pricePerSession = price.toDouble()
                    )
                    useCases.updateStudent(updatedStudent)
                }
            }
        }
    }


    fun markAbsent(studentId: Int) {
        viewModelScope.launch {
            val result = useCases.markAbsent(studentId)
            _attendanceResult.value = result
        }
    }

    fun deleteStudent(student: Student) {
        viewModelScope.launch {
            useCases.deleteStudent(student)
        }
    }
}