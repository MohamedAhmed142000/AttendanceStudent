package com.example.attendancestudent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.attendancestudent.data.local.AttendanceDatabase
import com.example.attendancestudent.data.repository.StudentRepositoryImpl
import com.example.attendancestudent.domain.usecase.student.delete.DeleteStudentUseCase
import com.example.attendancestudent.domain.usecase.student.increment.IncrementSessionUseCase
import com.example.attendancestudent.domain.usecase.student.insert.InsertStudentUseCase
import com.example.attendancestudent.domain.usecase.student.search.SearchStudentsByNameUseCase
import com.example.attendancestudent.domain.usecase.student.StudentUseCases
import com.example.attendancestudent.domain.usecase.student.update.UpdatePaidSessionsUseCase
import com.example.attendancestudent.domain.usecase.student.update.UpdateStudentUseCase
import com.example.attendancestudent.domain.usecase.student.update.UpdateYearPriceUseCase
import com.example.attendancestudent.domain.usecase.student.get.GetAllPricesUseCase
import com.example.attendancestudent.domain.usecase.student.get.GetAllStudentsUseCase
import com.example.attendancestudent.domain.usecase.student.get.GetStudentByIdUseCase
import com.example.attendancestudent.domain.usecase.student.get.GetStudentsBySubject
import com.example.attendancestudent.domain.usecase.student.get.GetStudentsByYear
import com.example.attendancestudent.domain.usecase.student.get.GetStudentsByYearAndSubject
import com.example.attendancestudent.presentation.student.screen.AppNavHost
import com.example.attendancestudent.presentation.student.viewmodel.StudentViewModel
import com.jakewharton.threetenabp.AndroidThreeTen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidThreeTen.init(this)
        val db = Room.databaseBuilder(
            applicationContext,
            AttendanceDatabase::class.java,
            "attendance_db"
        ).fallbackToDestructiveMigration() // تحذف القاعدة القديمة وتبني واحدة جديدة
//.addMigrations(MIGRATION_1_2,MIGRATION_2_3,MIGRATION_3_4,MIGRATION_4_5) // ✅ هنا بتستخدم الـ migration
            .build()

        val repo = StudentRepositoryImpl(db.studentDao(), db.yearPriceDao())
        val useCases = StudentUseCases(
            insertStudent = InsertStudentUseCase(repo),
            getAllStudents = GetAllStudentsUseCase(repo),
            deleteStudent = DeleteStudentUseCase(repo),
            updateStudent = UpdateStudentUseCase(repo),
            getStudentById = GetStudentByIdUseCase(repo),
            incrementSession = IncrementSessionUseCase(repo),
            getAllPrices = GetAllPricesUseCase(repo),
            updatePrice = UpdateYearPriceUseCase(repo),
            searchStudents = SearchStudentsByNameUseCase(repo),

            getStudentsBySubject = GetStudentsBySubject(repo),
            getStudentsByYearAndSubject = GetStudentsByYearAndSubject(repo),
            getStudentsByYear = GetStudentsByYear(repo),
            updatePaidSessions = UpdatePaidSessionsUseCase(repo)

        )

        val viewModel = StudentViewModel(useCases)

        setContent {
            val navController = rememberNavController()
            AppNavHost(navController = navController, viewModel = viewModel)
        }
    }
}
