package com.ch13.gamepad

import com.badlogic.gdx.controllers.PovDirection

object XBoxGamepad {
    const val BUTTON_A = 0
    const val BUTTON_B = 1
    const val BUTTON_X = 2
    const val BUTTON_Y = 3
    const val BUTTON_LEFT_SHOULDER = 4
    const val BUTTON_RIGHT_SHOULDER = 5
    const val BUTTON_BACK = 6
    const val BUTTON_START = 7
    const val BUTTON_LEFT_STICK = 8
    const val BUTTON_RIGHT_STICK = 9

    val DPAD_UP = PovDirection.north
    val DPAD_DOWN = PovDirection.south
    val DPAD_RIGHT = PovDirection.east
    val DPAD_LEFT = PovDirection.west

    // X-axis: -1 = left, +1 = right
    // Y-axis: -1 = up  , +1 = down
    const val AXIS_LEFT_X = 1
    const val AXIS_LEFT_Y = 0
    const val AXIS_RIGHT_X = 3
    const val AXIS_RIGHT_Y = 2

    // Left & Right Trigger buttons treated as a single axis; same ID value
    // Values - Left trigger: 0 to +1.  Right trigger: 0 to -1.
    // Note: values are additive; they can cancel each other if both are pressed!
    const val AXIS_LEFT_TRIGGER = 4
    const val AXIS_RIGHT_TRIGGER = 4
}