package com.ch08.brickbreaker

import com.BaseActor
import com.badlogic.gdx.scenes.scene2d.Stage
import kotlin.math.abs


class Ball(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    var isPaused: Boolean = true

    init {
        loadTexture("assets/ch08/breakout/ball.png")
        speed = 400f
        motionAngle = 90f
        setBoundaryPolygon(12)
    }

    fun bounceOff(other: BaseActor) {
        val v = this.preventOverlap(other) ?: return
        if (abs(v.x) >= abs(v.y))
        // horizontal bounce
            this.velocityVec.x *= -1f
        else
        // vertical bounce
            this.velocityVec.y *= -1f
    }

    override fun act(dt: Float) {
        super.act(dt)

        if (!isPaused) {
            // simulate gravity
            acceleration = 10f
            accelerateAtAngle(270f)
            applyPhysics(dt)
        }
    }
}