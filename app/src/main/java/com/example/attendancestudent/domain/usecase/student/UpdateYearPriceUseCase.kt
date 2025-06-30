package com.example.attendancestudent.domain.usecase.student

import com.example.attendancestudent.data.repository.StudentRepositoryImpl


class UpdateYearPriceUseCase(private val repository: StudentRepositoryImpl) {
    suspend operator fun invoke(year: String, newPrice: Int) =
        repository.updatePrice(year, newPrice)
}