package com.example.attendancestudent.domain.usecase.student.get

import com.example.attendancestudent.domain.model.Student
import com.example.attendancestudent.domain.repository.StudentRepository
import kotlinx.coroutines.flow.Flow

class GetStudentsBySubject(private val repository: StudentRepository) {
    operator fun invoke(subject: String): Flow<List<Student>> = repository.getStudentsBySubject(subject)
}