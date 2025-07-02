package com.example.attendancestudent.domain.usecase.student

import com.example.attendancestudent.domain.usecase.student.get.GetAllPricesUseCase
import com.example.attendancestudent.domain.usecase.student.get.GetAllStudentsUseCase
import com.example.attendancestudent.domain.usecase.student.get.GetStudentByIdUseCase
import com.example.attendancestudent.domain.usecase.student.get.GetStudentsBySubject
import com.example.attendancestudent.domain.usecase.student.get.GetStudentsByYear
import com.example.attendancestudent.domain.usecase.student.get.GetStudentsByYearAndSubject

data class StudentUseCases(
    val insertStudent: InsertStudentUseCase,
    val getAllStudents: GetAllStudentsUseCase,
    val deleteStudent: DeleteStudentUseCase,
    val updateStudent: UpdateStudentUseCase,
    val getStudentById: GetStudentByIdUseCase,
    val incrementSession: IncrementSessionUseCase,
    val getAllPrices: GetAllPricesUseCase,
    val updatePrice: UpdateYearPriceUseCase,
    val searchStudents: SearchStudentsByNameUseCase,
    val getStudentsBySubject: GetStudentsBySubject,
    val getStudentsByYearAndSubject: GetStudentsByYearAndSubject,
    val getStudentsByYear: GetStudentsByYear,
    val updatePaidSessions: UpdatePaidSessionsUseCase



)