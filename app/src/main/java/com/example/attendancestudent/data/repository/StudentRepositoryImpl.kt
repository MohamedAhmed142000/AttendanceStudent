package com.example.attendancestudent.data.repository

import com.example.attendancestudent.data.local.dao.StudentDao
import com.example.attendancestudent.data.local.dao.YearPriceDao
import com.example.attendancestudent.data.local.entity.StudentEntity
import com.example.attendancestudent.data.local.entity.YearPriceEntity
import com.example.attendancestudent.domain.model.Student
import com.example.attendancestudent.domain.repository.StudentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StudentRepositoryImpl(
    private val dao: StudentDao,
    private val yearPriceDao: YearPriceDao // ✅ لازم تمرره
) : StudentRepository {

    override suspend fun insertStudent(student: Student) {
        dao.insertStudent(student.toEntity())
    }

    override suspend fun updateStudent(student: Student) {
        dao.updateStudent(student.toEntity())
    }

    override suspend fun deleteStudent(student: Student) {
        dao.deleteStudent(student.toEntity())
    }

    override fun getAllStudents(): Flow<List<Student>> {
        return dao.getAllStudents().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun getStudentById(id: Int): Student? {
        return dao.getStudentById(id)?.toDomain()
    }

    override suspend fun incrementSession(studentId: Int, today: String) {
        dao.incrementSessionIfNotAttendedToday(studentId, today)
    }

    fun getAllPrices(): Flow<List<YearPriceEntity>> = yearPriceDao.getAllPrices()
    suspend fun updatePrice(year: String, newPrice: Int) =
        yearPriceDao.insertOrUpdatePrice(YearPriceEntity(year, newPrice))

    suspend fun getPriceForYear(year: String): Int =
        yearPriceDao.getPriceForYear(year) ?: 0

    override fun searchStudentsByName(name: String): Flow<List<Student>> {
        return dao.searchStudentsByName(name).map { list ->
            list.map { it.toDomain() }}
    }
    override fun getStudentsBySubject(subject: String): Flow<List<Student>> {
        return dao.getStudentsBySubject(subject).map { list ->
            list.map { it.toDomain() }}
    }
  override  fun getStudentsByYearAndSubject(year: String, subject: String): Flow<List<Student>>{
      return dao.getStudentsByYearAndSubject(year, subject).map { list ->
          list.map { it.toDomain() }}
  }
    override fun getStudentsByYear(year: String): Flow<List<Student>> {
        return dao.getStudentsByYear(year).map { list ->
            list.map { it.toDomain() } // لو عندك دالة تحويل من Entity لـ Model
        }
    }


    // تحويلات
    private fun Student.toEntity(): StudentEntity {
        return StudentEntity(
            id = id,
            fullName = fullName,
            academicYear = academicYear,
            attendedSessions = attendedSessions,
            lastAttendanceDate = lastAttendanceDate,
            subject = subject // ✅


        )
    }

    private fun StudentEntity.toDomain(): Student {
        return Student(
            id = id,
            fullName = fullName,
            academicYear = academicYear,
            attendedSessions = attendedSessions,
            lastAttendanceDate = lastAttendanceDate,
            subject = subject // ✅


        )
    }
}