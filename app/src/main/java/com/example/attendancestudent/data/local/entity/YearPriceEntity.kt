package com.example.attendancestudent.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "year_prices", primaryKeys = ["year", "subject"])
data class YearPriceEntity(
     val year: String,
     val subject: String,
    val pricePerSession: Int
)
