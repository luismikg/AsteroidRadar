package com.udacity.asteroidradar.main.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.database.AsteroidRadarDatabase
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.net.Api
import com.udacity.asteroidradar.repository.AsteroidsRadarRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(asteroidRadarDatabase: AsteroidRadarDatabase) : ViewModel() {

    private var asteroidsRadarRepository = AsteroidsRadarRepository(asteroidRadarDatabase)
    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    init {
        getImageDay()
        getAsteroids()
    }

    private fun getImageDay() {
        viewModelScope.launch {
            val response = Api.retrofitService.getImageDay(Constants.API_KEY)
            _pictureOfDay.value = response
        }
    }

    private fun getAsteroids() {

        val calendar: Calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT)

        val today = dateFormat.format(calendar.time)

        calendar.add(Calendar.DATE, 7)
        val nextSevenDay = dateFormat.format(calendar.time)

        viewModelScope.launch {
            try {
                asteroidsRadarRepository.getAsteroids(Constants.API_KEY, today, nextSevenDay)
            } catch (e: Exception) {
                Log.i("TEST", e.toString())
            }
        }
    }

    fun getOnAsteroids(): LiveData<List<Asteroid>> = asteroidsRadarRepository.asteroids
}