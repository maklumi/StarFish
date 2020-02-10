package com.ch07.sidescrolling

import com.badlogic.gdx.scenes.scene2d.Stage
import com.BaseActor


class Sky(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadTexture("assets/ch07/plane/sky.png")
        speed = 25f
        motionAngle = 180f
    }

    override fun act(dt: Float) {
        super.act(dt)
        applyPhysics(dt)

        // if moved completely past left edge of screen:
        //   shift right, past other instance.
        if (x + width < 0) {
            moveBy(2 * width, 0f)
        }
    }

}