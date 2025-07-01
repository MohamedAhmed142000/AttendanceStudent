package com.example.attendancestudent.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.attendancestudent.data.local.entity.YearPriceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface YearPriceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdatePrice(price: YearPriceEntity)

    @Query("SELECT * FROM year_prices")
    fun getAllPrices(): Flow<List<YearPriceEntity>>

    @Query("SELECT pricePerSession FROM year_prices WHERE year = :year LIMIT 1")
    suspend fun getPriceForYear(year: String): Int?

    @Query("SELECT * FROM year_prices WHERE year = :year AND subject = :subject")
    suspend fun getPriceForYearAndSubject(year: String, subject: String): YearPriceEntity?

}
