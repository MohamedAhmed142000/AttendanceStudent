package com.example.attendancestudent.domain.usecase.student

import com.example.attendancestudent.domain.repository.StudentRepository

class ResetSessionsUseCase(
    private val repository: StudentRepository
) {
    suspend operator fun invoke(studentId: Int) {
        val student = repository.getStudentById(studentId) ?: return
        val updatedStudent = student.copy(attendedSessions = 0)
        repository.updateStudent(updatedStudent)
    }
}
