package com.example.attendancestudent.domain.usecase.student.insert

import com.example.attendancestudent.domain.model.Student
import com.example.attendancestudent.domain.repository.StudentRepository

class InsertStudentUseCase( private val repository: StudentRepository) {
    suspend operator fun invoke(student: Student) {
        repository.insertStudent(student)
    }
}