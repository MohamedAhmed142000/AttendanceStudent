package com.example.attendancestudent.domain.usecase.student.mark

import com.example.attendancestudent.domain.repository.StudentRepository

class MarkAbsent(private val repository: StudentRepository) {
    suspend operator fun invoke(studentId: Int): Boolean {
        val student = repository.getStudentById(studentId)
        if (student != null && student.attendedSessions == 0 && !student.isAbsent) {
            val updated = student.copy(isAbsent = true, absentCount = student.absentCount + 1)
            repository.updateStudent(updated)
            return true
        }
        return false // مينفعش نسجل غياب لو حضر
    }
}
