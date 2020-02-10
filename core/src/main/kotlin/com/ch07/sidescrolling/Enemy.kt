package com.ch07.sidescrolling

import com.BaseActor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.TimeUtils

class Enemy(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        val filenames = Array<String>()
        filenames.addAll("assets/ch07/plane/planeRed0.png",
                         "assets/ch07/plane/planeRed1.png",
                         "assets/ch07/plane/planeRed2.png",
                         "assets/ch07/plane/planeRed1.png")

        loadAnimationFromFiles(filenames, 0.1f, true)

        speed = 100f
        motionAngle = 180f - 45f
        setBoundaryPolygon(8)
    }

    private var startTime = TimeUtils.millis()
    private var deg = 0f

    override fun act(dt: Float) {
        super.act(dt)
        applyPhysics(dt)
        sineWave()
    }

    private fun sineWave() {
        if (TimeUtils.timeSinceMillis(startTime) > 100) {
            deg = (deg + 1) % 4
            motionAngle += deg
            startTime = TimeUtils.millis()
        }
    }
}