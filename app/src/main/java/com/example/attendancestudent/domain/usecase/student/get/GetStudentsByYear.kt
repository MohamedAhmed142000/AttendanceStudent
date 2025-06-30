package com.example.attendancestudent.domain.usecase.student.get

import com.example.attendancestudent.domain.model.Student
import com.example.attendancestudent.domain.repository.StudentRepository
import kotlinx.coroutines.flow.Flow

class GetStudentsByYear(private val repository: StudentRepository) {
    operator fun invoke(year: String): Flow<List<Student>> {
        return repository.getStudentsByYear(year)
    }
}
