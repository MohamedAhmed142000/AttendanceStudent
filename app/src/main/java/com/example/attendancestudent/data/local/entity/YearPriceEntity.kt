package com.example.attendancestudent.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "year_prices")
data class YearPriceEntity(
    @PrimaryKey val year: String,
    val pricePerSession: Int
)
