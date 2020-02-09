package com.ch04

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions


class Rock(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {

    init {
        loadTexture("ch04/rock.png")

        val random = MathUtils.random(30f)

        addAction(Actions.forever(Actions.rotateBy(30 + random, 1f)))

        speed = 50 + random
        setMaxSpeed(50 + random)
        setDeceleration(0f)

        motionAngle = MathUtils.random(360f)
    }

    override fun act(dt: Float) {
        super.act(dt)
        applyPhysics(dt)
        wrapAroundWorld()
    }

}