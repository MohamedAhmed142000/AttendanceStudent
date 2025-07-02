package com.example.attendancestudent.domain.usecase.student.update

import com.example.attendancestudent.domain.repository.StudentRepository

class UpdatePaidSessionsUseCase(private val studentRepository: StudentRepository)  {
    suspend operator fun invoke(studentId: Int, paidSessions: Int) {
        studentRepository.updatePaidSessions(studentId, paidSessions)
    }

}