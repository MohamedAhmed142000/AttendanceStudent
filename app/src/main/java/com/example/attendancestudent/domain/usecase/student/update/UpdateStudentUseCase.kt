package com.example.attendancestudent.domain.usecase.student.update

import com.example.attendancestudent.domain.model.Student
import com.example.attendancestudent.domain.repository.StudentRepository

class UpdateStudentUseCase (private val repository: StudentRepository) {
    suspend operator fun invoke(student: Student) = repository.updateStudent(student)
}