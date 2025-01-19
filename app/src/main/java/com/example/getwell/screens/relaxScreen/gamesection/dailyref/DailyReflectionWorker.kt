package com.example.getwell.screens.relaxScreen.gamesection.dailyref

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.getwell.MainActivity
import com.example.getwell.R
import java.util.Calendar
import java.util.concurrent.TimeUnit

class DailyReflectionWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        // Create notification
        createNotification()
        // Schedule next day's work
        scheduleNextDay()
        return Result.success()
    }

    private fun createNotification() {
        val notificationManager = applicationContext.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        // Create notification channel for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "daily_reflection",
                "Daily Reflection",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Channel for Daily Reflection notifications"
                enableVibration(true)
                vibrationPattern = longArrayOf(0, 500, 1000) // pattern: wait, vibrate, wait
                setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), null)
            }
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(applicationContext, "daily_reflection")
            .setSmallIcon(io.getstream.chat.android.compose.R.drawable.stream_ic_notification)
            .setContentTitle("Time for Daily Reflection")
            .setContentText("What's on your mind today?")
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)) // Set custom sound
            .build()

        notificationManager.notify(1, notification)
    }

    private fun scheduleNextDay() {
        val calendar = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, 1)
            set(Calendar.HOUR_OF_DAY, 9) // Set to 9 AM
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        val delay = calendar.timeInMillis - System.currentTimeMillis()

        val workRequest = OneTimeWorkRequestBuilder<DailyReflectionWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(applicationContext)
            .enqueue(workRequest)
    }

    companion object {
        fun schedule(context: Context) {
            val calendar = Calendar.getInstance()
            val now = calendar.timeInMillis

            calendar.apply {
                set(Calendar.HOUR_OF_DAY, 9) // Set to 9 AM
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
            }

//             If it's past 9 AM, schedule for next day
            if (now > calendar.timeInMillis) {
                calendar.add(Calendar.DAY_OF_YEAR, 1)
            }

            val delay = calendar.timeInMillis - now

            val workRequest = OneTimeWorkRequestBuilder<DailyReflectionWorker>()
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .build()

            WorkManager.getInstance(context)
                .enqueue(workRequest)
        }
    }
}