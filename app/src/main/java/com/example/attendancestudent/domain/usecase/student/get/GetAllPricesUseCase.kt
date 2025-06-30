package com.example.attendancestudent.domain.usecase.student.get

import com.example.attendancestudent.data.local.entity.YearPriceEntity
import com.example.attendancestudent.data.repository.StudentRepositoryImpl
import kotlinx.coroutines.flow.Flow

class GetAllPricesUseCase(private val repository: StudentRepositoryImpl) {
    operator fun invoke(): Flow<List<YearPriceEntity>> = repository.getAllPrices()
}
