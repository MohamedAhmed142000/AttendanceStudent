package com.example.attendancestudent.domain.usecase.student

import com.example.attendancestudent.domain.model.Student
import com.example.attendancestudent.domain.repository.StudentRepository
import kotlinx.coroutines.flow.Flow

class SearchStudentsByNameUseCase(
    private val repository: StudentRepository
) {
    operator fun invoke(name: String): Flow<List<Student>> {
        return repository.searchStudentsByName(name)
    }
}
