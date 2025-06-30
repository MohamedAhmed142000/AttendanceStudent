package com.example.attendancestudent.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // إنشاء جدول year_prices لو مش موجود
        database.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `year_prices` (
                `year` TEXT NOT NULL,
                `pricePerSession` INTEGER NOT NULL,
                PRIMARY KEY(`year`)
            )
        """.trimIndent()
        )
    }
}
val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // إضافة عمود lastAttendanceDate إلى جدول الطلاب لو ضفته في Student Entity
        database.execSQL(
            """
            ALTER TABLE students ADD COLUMN lastAttendanceDate TEXT
        """.trimIndent()
        )
    }
}
val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            """
            ALTER TABLE students ADD COLUMN subject TEXT NOT NULL DEFAULT ''
            """.trimIndent()
        )
    }
}
