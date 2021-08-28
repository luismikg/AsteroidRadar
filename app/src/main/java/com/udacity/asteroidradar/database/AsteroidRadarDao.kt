package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AsteroidRadarDao {
    @Query("SELECT * FROM asteroid_radar_table ORDER BY close_approach_date ASC")
    fun getAsteroids(): LiveData<List<AsteroidRadar>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSet(asteroids: List<AsteroidRadar>)
}