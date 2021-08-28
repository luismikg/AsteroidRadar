package com.udacity.asteroidradar.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Asteroid(
    val id: Long? = null,
    val codename: String? = null,
    val closeApproachDate: String? = null,
    val absoluteMagnitude: Double? = null,
    val estimatedDiameter: Double? = null,
    val relativeVelocity: Double? = null,
    val distanceFromEarth: Double? = null,
    val isPotentiallyHazardous: Boolean? = null
) : Parcelable