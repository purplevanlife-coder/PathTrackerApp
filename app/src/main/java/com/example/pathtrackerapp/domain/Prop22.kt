package com.example.pathtrackerapp.domain

import kotlin.math.max

/** Vehicle mode for a delivery, matching Collin's Prop 22 Space. */
enum class Vehicle(val key: String) {
    CAR("car"),
    EBIKE("ebike");

    companion object {
        fun fromKey(key: String): Vehicle = entries.firstOrNull { it.key == key } ?: CAR
    }
}

/**
 * Prop 22 math, identical to the Prop 22 Space:
 * guarantee = engaged hours x engaged rate + (car miles x per-mile rate, car mode only)
 * top-up = max(0, guarantee - base/promotion pay); tips are on top and never
 * count toward the guarantee. Expected pay = base + tips + top-up.
 */
object Prop22Calculator {
    const val ENGAGED_RATE_PER_HOUR = 20.28
    const val PER_MILE_RATE = 0.37
    const val METERS_PER_MILE = 1609.344

    fun miles(distanceMeters: Double): Double = distanceMeters / METERS_PER_MILE

    fun averageSpeedMph(distanceMeters: Double, durationSeconds: Long): Double {
        if (durationSeconds <= 0) return 0.0
        return miles(distanceMeters) / (durationSeconds / 3600.0)
    }

    fun guarantee(durationSeconds: Long, distanceMeters: Double, vehicle: Vehicle): Double {
        val hours = durationSeconds / 3600.0
        val mileagePart = if (vehicle == Vehicle.CAR) miles(distanceMeters) * PER_MILE_RATE else 0.0
        return hours * ENGAGED_RATE_PER_HOUR + mileagePart
    }

    fun topUp(guarantee: Double, basePay: Double): Double = max(0.0, guarantee - basePay)

    fun expectedTotal(basePay: Double, tips: Double, topUp: Double): Double =
        basePay + tips + topUp
}
