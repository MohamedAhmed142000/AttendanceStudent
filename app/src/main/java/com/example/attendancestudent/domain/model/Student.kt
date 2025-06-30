package com.example.attendancestudent.domain.model

import com.example.attendancestudent.data.local.entity.StudentEntity

data class Student(
    val id: Int = 0,
    val fullName: String,
    val academicYear: String,
    val attendedSessions: Int = 0,
    val lastAttendanceDate: String? = null,
    val subject: String // ✅ أضف هذا السطر

)
