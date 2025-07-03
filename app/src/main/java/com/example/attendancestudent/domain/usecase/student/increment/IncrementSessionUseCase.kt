package com.example.attendancestudent.domain.usecase.student.increment

import com.example.attendancestudent.domain.repository.StudentRepository
import org.threeten.bp.LocalDate
class IncrementSessionUseCase(
    private val repository: StudentRepository
) {
    suspend operator fun invoke(studentId: Int): Boolean {
        val student = repository.getStudentById(studentId) ?: return false

        val today = LocalDate.now().toString() // e.g., "2025-06-29"
        if (student.lastAttendanceDate == today) {
            return false // الحضور بالفعل مسجل النهاردة
        }
        val updated = student.copy(
            attendedSessions = student.attendedSessions + 1,
            lastAttendanceDate = today,
            isAbsent = false,
            absentCount =
                if (student.isAbsent && student.absentCount > 0) student.absentCount - 1 else student.absentCount
        )
        repository.updateStudent(updated)
        return true
    }
}


