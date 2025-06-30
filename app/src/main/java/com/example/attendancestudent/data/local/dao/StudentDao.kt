package com.example.attendancestudent.data.local.dao

import androidx.room.*
import com.example.attendancestudent.data.local.entity.StudentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: StudentEntity)

    @Update
    suspend fun updateStudent(student: StudentEntity)

    @Delete
    suspend fun deleteStudent(student: StudentEntity)

    @Query("SELECT * FROM students")
    fun getAllStudents(): Flow<List<StudentEntity>>

    @Query("SELECT * FROM students WHERE id = :id")
    suspend fun getStudentById(id: Int): StudentEntity?

    @Query("UPDATE students SET attendedSessions = attendedSessions + 1 WHERE id = :studentId")
    suspend fun incrementSession(studentId: Int)

    @Query("UPDATE students SET attendedSessions = attendedSessions + 1, lastAttendanceDate = :date WHERE id = :studentId")
    suspend fun incrementSessionIfNotAttendedToday(studentId: Int, date: String)

    @Query("SELECT * FROM students WHERE fullName LIKE '%' || :name || '%'")
    fun searchStudentsByName(name: String): Flow<List<StudentEntity>>

    @Query("SELECT * FROM students WHERE subject = :subject")
    fun getStudentsBySubject(subject: String): Flow<List<StudentEntity>>

    @Query("SELECT * FROM students WHERE academicYear = :year AND subject = :subject")
    fun getStudentsByYearAndSubject(year: String, subject: String): Flow<List<StudentEntity>>

    @Query("SELECT * FROM students WHERE academicYear = :year")
    fun getStudentsByYear(year: String): Flow<List<StudentEntity>>

}
