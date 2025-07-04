package com.example.attendancestudent.domain.usecase.student.update

import com.example.attendancestudent.data.repository.StudentRepositoryImpl


class UpdateYearPriceUseCase(private val repository: StudentRepositoryImpl) {
    suspend operator fun invoke(year: String,subject:String, newPrice: Int) =
        repository.updatePrice(year, subject,newPrice)
}