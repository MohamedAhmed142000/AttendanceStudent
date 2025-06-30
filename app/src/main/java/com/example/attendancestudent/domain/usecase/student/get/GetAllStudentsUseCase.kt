package com.example.attendancestudent.domain.usecase.student.get

import com.example.attendancestudent.domain.repository.StudentRepository

class GetAllStudentsUseCase(private val repository: StudentRepository) {
    operator fun invoke() = repository.getAllStudents()
}