package com.example.attendancestudent.data.repository

import com.example.attendancestudent.data.local.dao.StudentDao
import com.example.attendancestudent.data.local.dao.YearPriceDao
import com.example.attendancestudent.data.local.entity.StudentEntity
import com.example.attendancestudent.data.local.entity.YearPriceEntity
import com.example.attendancestudent.domain.model.Student
import com.example.attendancestudent.domain.repository.StudentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class StudentRepositoryImpl(
    private val dao: StudentDao,
    private val yearPriceDao: YearPriceDao // ✅ لازم تمرره
) : StudentRepository {

    override fun getAllStudents(): Flow<List<Student>> {
        return dao.getAllStudents().map { list ->
            list.map { it.toDomain(pricePerSession = 0.0) }
        }
    }

    override suspend fun insertStudent(student: Student) {
        dao.insertStudent(student.toEntity())
    }

    override suspend fun updateStudent(student: Student) {
        dao.updateStudent(student.toEntity())
    }

    override suspend fun deleteStudent(student: Student) {
        dao.deleteStudent(student.toEntity())
    }


    override suspend fun getStudentById(id: Int): Student? {
        val entity = dao.getStudentById(id) ?: return null
        val price = yearPriceDao.getPriceFor(entity.academicYear, entity.subject)?.pricePerSession ?: 0
        return entity.toDomain(price.toDouble())    }

    fun getAllPrices(): Flow<List<YearPriceEntity>> = yearPriceDao.getAllPrices()

    override suspend fun incrementSession(studentId: Int, today: String) {
        dao.incrementSessionIfNotAttendedToday(studentId, today)
    }


    suspend fun updatePrice(year: String, subject: String, newPrice: Int) =
        yearPriceDao.insertOrUpdatePrice(YearPriceEntity(year, subject, newPrice))



    override fun searchStudentsByName(name: String): Flow<List<Student>> {
        return dao.searchStudentsByName(name).map { list ->
            list.map { it.toDomain(pricePerSession = 0.0) }
        }
    }

    override fun getStudentsBySubject(subject: String): Flow<List<Student>> {
        return dao.getStudentsBySubject(subject).map { list ->
            list.map { it.toDomain(pricePerSession = 0.0) }
        }
    }

    override fun getStudentsByYearAndSubject(year: String, subject: String): Flow<List<Student>> {
        return dao.getStudentsByYearAndSubject(year, subject).map { list ->
            list.map { it.toDomain(pricePerSession = 0.0) }
        }
    }

    override fun getStudentsByYear(year: String): Flow<List<Student>> {
        return dao.getStudentsByYear(year).map { list ->
            list.map { it.toDomain(pricePerSession = 0.0) } // لو عندك دالة تحويل من Entity لـ Model
        }
    }

    override suspend fun updatePaidSessions(studentId: Int, paidSessions: Int) {
         dao.updatePaidSessions(studentId, paidSessions)
    }

    fun getAllStudentsWithPrice(): Flow<List<Student>> = dao.getAllStudents().map { studentList ->
        studentList.map { studentEntity ->
            val year = studentEntity.academicYear
            val subject = studentEntity.subject

            // suspend function مش مسموح داخل map، فلازم نعمل حل بديل
            val price = runBlocking {
                yearPriceDao.getPriceFor(year, subject)?.pricePerSession?.toDouble() ?: 0.0
            }

            studentEntity.toDomain(price)
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
            subject = subject ,
            paidSessions = paidSessions,

        )
    }

    private fun StudentEntity.toDomain(pricePerSession: Double): Student {
        return Student(
            id = id,
            fullName = fullName,
            academicYear = academicYear,
            attendedSessions = attendedSessions,
            lastAttendanceDate = lastAttendanceDate,
            subject = subject,
            paidSessions = paidSessions,
                    pricePerSession = pricePerSession


        )
    }
}