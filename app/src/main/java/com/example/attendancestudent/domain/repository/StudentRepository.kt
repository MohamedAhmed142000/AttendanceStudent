package com.example.attendancestudent.domain.repository

import com.example.attendancestudent.domain.model.Student
import com.example.attendancestudent.domain.model.YearPrice
import kotlinx.coroutines.flow.Flow

interface StudentRepository {
    suspend fun insertStudent(student: Student)
    suspend fun updateStudent(student: Student)
    suspend fun deleteStudent(student: Student)
    fun getAllStudents(): Flow<List<Student>>
    suspend fun getStudentById(id: Int): Student?
    suspend fun incrementSession(studentId: Int,today:String)
    fun searchStudentsByName(name: String): Flow<List<Student>>
    fun getStudentsBySubject(subject: String): Flow<List<Student>>
    fun getStudentsByYearAndSubject(year: String, subject: String): Flow<List<Student>>
    fun getStudentsByYear(year: String): Flow<List<Student>>
suspend fun updatePaidSessions(studentId: Int, paidSessions: Int)


}