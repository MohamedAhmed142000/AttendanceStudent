package com.example.attendancestudent.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class StudentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fullName: String,
    val academicYear: String,
    val attendedSessions: Int = 0,
    val lastAttendanceDate: String? = null,
    val subject: String ,// ✅ مضاف جديد
    val paidSessions: Int = 0 // الحصة المدفوعة
    ,val isAbsent: Boolean = false,
    val absentCount: Int = 0

)
