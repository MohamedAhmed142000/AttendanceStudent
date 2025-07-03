package com.example.attendancestudent.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.attendancestudent.data.local.entity.StudentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {
    //todo get all students
    @Query("SELECT * FROM students")
    fun getAllStudents(): Flow<List<StudentEntity>>

    //todo insert student
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: StudentEntity)

    //todo update student
    @Update
    suspend fun updateStudent(student: StudentEntity)

    //todo delete student
    @Delete
    suspend fun deleteStudent(student: StudentEntity)

    //todo get student by id
    @Query("SELECT * FROM students WHERE id = :id")
    suspend fun getStudentById(id: Int): StudentEntity?

    //todo increment session
    @Query("UPDATE students SET attendedSessions = attendedSessions + 1 WHERE id = :studentId")
    suspend fun incrementSession(studentId: Int)

    //todo increment session if not attended today
    @Query("UPDATE students SET attendedSessions = attendedSessions + 1, lastAttendanceDate = :date WHERE id = :studentId")
    suspend fun incrementSessionIfNotAttendedToday(studentId: Int, date: String)

    //todo search students by name
    @Query("SELECT * FROM students WHERE fullName LIKE '%' || :name || '%'")
    fun searchStudentsByName(name: String): Flow<List<StudentEntity>>

    //todo get students by subject
    @Query("SELECT * FROM students WHERE subject = :subject")
    fun getStudentsBySubject(subject: String): Flow<List<StudentEntity>>

    //todo get students by year and subject
    @Query("SELECT * FROM students WHERE academicYear = :year AND subject = :subject")
    fun getStudentsByYearAndSubject(year: String, subject: String): Flow<List<StudentEntity>>

    //todo get students by year
    @Query("SELECT * FROM students WHERE academicYear = :year")
    fun getStudentsByYear(year: String): Flow<List<StudentEntity>>

    //todo update paid sessions
    @Query("UPDATE students SET paidSessions = :paidSessions WHERE id = :studentId")
    suspend fun updatePaidSessions(studentId: Int, paidSessions: Int)
    //todo updata
}
