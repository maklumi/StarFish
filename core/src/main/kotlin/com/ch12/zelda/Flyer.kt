package com.ch12.zelda

import com.BaseActor
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Stage

class Flyer(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadAnimationFromSheet("assets/ch12/zelda/enemy-flyer.png", 1, 4, 0.05f, true)
        setSize(48f, 48f)
        setBoundaryPolygon(6)

        speed = MathUtils.random(25f, 40f)
        motionAngle = MathUtils.random(0f, 360f)
    }

    override fun act(dt: Float) {
        super.act(dt)

        if (MathUtils.random(1, 120) == 1)
            motionAngle = MathUtils.random(0f, 360f)

        applyPhysics(dt)
        boundToWorld()
    }
}