package com.example.pathtrackerapp

import com.example.pathtrackerapp.domain.Prop22Calculator
import com.example.pathtrackerapp.domain.Vehicle
import org.junit.Assert.assertEquals
import org.junit.Test

class Prop22CalculatorTest {

    @Test
    fun miles_conversion() {
        assertEquals(1.0, Prop22Calculator.miles(1609.344), 0.001)
        assertEquals(0.0, Prop22Calculator.miles(0.0), 0.001)
    }

    @Test
    fun averageSpeed_tenMilesInOneHour_isTenMph() {
        assertEquals(10.0, Prop22Calculator.averageSpeedMph(16093.44, 3600), 0.01)
    }

    @Test
    fun averageSpeed_zeroDuration_isZero() {
        assertEquals(0.0, Prop22Calculator.averageSpeedMph(5000.0, 0), 0.001)
    }

    @Test
    fun guarantee_car_includesTimeAndMiles() {
        // 2 hours, 30 miles: 2 x $20.28 + 30 x $0.37 = $40.56 + $11.10 = $51.66
        val g = Prop22Calculator.guarantee(7200, 30 * 1609.344, Vehicle.CAR)
        assertEquals(51.66, g, 0.01)
    }

    @Test
    fun guarantee_ebike_isTimeOnly() {
        // Same trip on an e-bike: no per-mile part, 2 x $20.28 = $40.56
        val g = Prop22Calculator.guarantee(7200, 30 * 1609.344, Vehicle.EBIKE)
        assertEquals(40.56, g, 0.01)
    }

    @Test
    fun topUp_neverNegative() {
        assertEquals(0.0, Prop22Calculator.topUp(50.0, 60.0), 0.001)
        assertEquals(10.0, Prop22Calculator.topUp(50.0, 40.0), 0.001)
    }

    @Test
    fun expectedTotal_isBasePlusTipsPlusTopUp() {
        assertEquals(75.0, Prop22Calculator.expectedTotal(40.0, 25.0, 10.0), 0.001)
    }
}
