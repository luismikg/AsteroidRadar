package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidRadarDatabase
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.net.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidsRadarRepository(private val asteroidRadarDatabase: AsteroidRadarDatabase) {

    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(asteroidRadarDatabase.asteroidRadarDao.getAsteroids()) {
            it.map { asteroidRadar ->
                Asteroid(
                    id = asteroidRadar.id,
                    codename = asteroidRadar.codeName,
                    closeApproachDate = asteroidRadar.closeApproachDate,
                    absoluteMagnitude = asteroidRadar.absoluteMagnitude,
                    estimatedDiameter = asteroidRadar.estimatedDiameter,
                    relativeVelocity = asteroidRadar.relativeVelocity,
                    distanceFromEarth = asteroidRadar.distanceFromEarth,
                    isPotentiallyHazardous = asteroidRadar.isPotentiallyHazardous
                )
            }
        }

    suspend fun getAsteroids(apiKey: String, today: String, nextSevenDay: String) {
        withContext(Dispatchers.IO) {
            val asteroids = Api.retrofitService.getAsteroids(apiKey, today, nextSevenDay)
            val jsonResult = parseAsteroidsJsonResult(JSONObject(asteroids))

            asteroidRadarDatabase.asteroidRadarDao.insertSet(jsonResult)
        }
    }
}