package com.example.attendancestudent.domain.usecase.student.delete

import com.example.attendancestudent.domain.model.Student
import com.example.attendancestudent.domain.repository.StudentRepository

class DeleteStudentUseCase (private val repository: StudentRepository) {
    suspend operator fun invoke(student: Student) = repository.deleteStudent(student)
}