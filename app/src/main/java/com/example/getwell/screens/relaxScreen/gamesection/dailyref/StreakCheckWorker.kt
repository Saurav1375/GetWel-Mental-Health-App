package com.example.getwell.screens.relaxScreen.gamesection.dailyref

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.getwell.authSystem.AuthUiClient
import com.example.getwell.authSystem.UserData
import com.example.getwell.di.Injection
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.TimeUnit

class StreakCheckWorker(
    context: Context,
    params: WorkerParameters,
   userData: UserData?
) : CoroutineWorker(context, params) {
    private val repository: ReflectionRepository by lazy {
        val firestore = Injection.instance()
        ReflectionRepository(firestore, userData)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        return try {
            repository.checkAndResetStreak()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    companion object {
        fun schedulePeriodicWork() {
            val streakCheckRequest = PeriodicWorkRequestBuilder<StreakCheckWorker>(
                1, // Repeat every 1 day
                TimeUnit.DAYS
            ).build()

            WorkManager.getInstance()
                .enqueueUniquePeriodicWork(
                    "streak_check",
                    ExistingPeriodicWorkPolicy.KEEP,
                    streakCheckRequest
                )
        }
    }
}