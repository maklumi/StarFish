package com.ch07.sidescrolling

import com.BaseActor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Array


class Plane(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        val filenames = Array<String>()
        filenames.addAll("assets/ch07/plane/planeGreen0.png", "assets/ch07/plane/planeGreen1.png", "assets/ch07/plane/planeGreen2.png", "assets/ch07/plane/planeGreen1.png")

        loadAnimationFromFiles(filenames, 0.1f, true)

        maxSpeed = 800f
        setBoundaryPolygon(8)
    }

    // automatically called in each iteration of game loop
    override fun act(dt: Float) {
        super.act(dt)

        // simulate force of gravity
        acceleration = 800f
        accelerateAtAngle(270f) // points downward
        applyPhysics(dt)

        // stop plane from passing through the ground
        for (g in getList(this.stage, Ground::class)) {
            if (this.overlaps(g)) {
                speed = 0f // else it goes through wall
                preventOverlap(g)
            }
        }

        // stop plane from moving past top of screen
        if (y + height > worldBounds.height) {
            speed = 0f
            boundToWorld()
        }

    }

    //handle vertical movement of plane
    fun boost() {
        speed = 300f
        motionAngle = 90f
    }
}