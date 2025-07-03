package com.example.attendancestudent.domain.model

data class Student(
    val id: Int = 0,
    val fullName: String,
    val academicYear: String,
    val attendedSessions: Int = 0,
    val lastAttendanceDate: String? = null,
    val subject: String, // ✅ أضف هذا السطر
    val paidSessions: Int = 0, // 👈 جديد
    val pricePerSession: Double = 0.0
    , val isAbsent: Boolean = false,
    val absentCount: Int = 0


) {
    val remainingSessions: Int
        get() = attendedSessions - paidSessions

    val remainingCost: Double
        get() = remainingSessions * pricePerSession

}