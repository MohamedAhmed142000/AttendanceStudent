package com.example.attendancestudent.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.attendancestudent.data.local.dao.StudentDao
import com.example.attendancestudent.data.local.dao.YearPriceDao
import com.example.attendancestudent.data.local.entity.StudentEntity
import com.example.attendancestudent.data.local.entity.YearPriceEntity

@Database(
    entities = [StudentEntity::class, YearPriceEntity::class],
    version = 4,
    exportSchema = false
)
abstract class AttendanceDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao
    abstract fun yearPriceDao(): YearPriceDao

}
