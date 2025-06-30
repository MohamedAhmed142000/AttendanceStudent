package com.example.attendancestudent.domain.usecase.student.get

import com.example.attendancestudent.domain.model.Student
import com.example.attendancestudent.domain.repository.StudentRepository
import kotlinx.coroutines.flow.Flow

class GetStudentsByYearAndSubject(private val repository: StudentRepository)  {
   operator fun invoke(year: String, subject: String): Flow<List<Student>> = repository.getStudentsByYearAndSubject(year, subject)
}