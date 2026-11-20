package com.example.pathtrackerapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TrackingSession(
    val id: String,
    val title: String,
    val startTimeMillis: Long,
    val durationSeconds: Long,
    val distanceMeters: Double,
    val averageSpeedKmh: Double,
    val steps: Int,
    val vehicle: String = "car",
    val basePay: Double = 0.0,
    val tips: Double = 0.0,
    val points: List<SessionPoint>
)
