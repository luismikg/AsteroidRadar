package com.udacity.asteroidradar

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AsteroidRadarDatabase
import com.udacity.asteroidradar.repository.AsteroidsRadarRepository
import retrofit2.HttpException

class UpdateDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val database = AsteroidRadarDatabase.getInstance(applicationContext)
        val asteroidsRadarRepository = AsteroidsRadarRepository(database)
        return try {

            val calendar: java.util.Calendar = java.util.Calendar.getInstance()
            val dateFormat =
                java.text.SimpleDateFormat(com.udacity.asteroidradar.Constants.API_QUERY_DATE_FORMAT)

            val today = dateFormat.format(calendar.time)

            calendar.add(java.util.Calendar.DATE, 7)
            val nextSevenDay = dateFormat.format(calendar.time)
            asteroidsRadarRepository.getAsteroids(
                com.udacity.asteroidradar.Constants.API_KEY,
                today,
                nextSevenDay
            )

            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }

    companion object {
        const val WORK_NAME = "UpdateDataWorker"
    }
}