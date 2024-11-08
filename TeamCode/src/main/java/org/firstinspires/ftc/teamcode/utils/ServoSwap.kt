package org.firstinspires.ftc.teamcode.utils

import com.qualcomm.robotcore.hardware.Servo

class ServoSwap(val servo: Servo, val Pos1: Double, val Pos2: Double, private val rising: Boolean = true) {
    val detect: Detector = Detector()
    var state = false
    fun update(Value: Boolean) {
        detect.update(Value)
        if (rising && detect.risingEdge()) {
            state = !state
        } else if (!rising && detect.fallingEdge()) {
            state = !state
        }
        if (state) {
            servo.position = Pos1
        } else {
            servo.position = Pos2
        }
    }

    fun set(value: Boolean) {
        state = value
        if (state) {
            servo.position = Pos1
        } else {
            servo.position = Pos2
        }
    }

    fun swap() {
        state = !state
        if (state) {
            servo.position = Pos1
        } else {
            servo.position = Pos2
        }
    }
}