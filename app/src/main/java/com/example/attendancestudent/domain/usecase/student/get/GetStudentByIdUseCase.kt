package com.example.attendancestudent.domain.usecase.student.get

import com.example.attendancestudent.domain.repository.StudentRepository

class GetStudentByIdUseCase (private val repository: StudentRepository) {
    suspend operator fun invoke(id: Int) = repository.getStudentById(id)
}