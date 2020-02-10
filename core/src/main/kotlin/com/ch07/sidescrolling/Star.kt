package com.ch07.sidescrolling

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.BaseActor


class Star(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadTexture("assets/ch07/plane/star.png")

        val pulse = Actions.sequence(
                Actions.scaleTo(1.2f, 1.2f, 0.5f),
                Actions.scaleTo(1.0f, 1.0f, 0.5f))
        addAction(Actions.forever(pulse))

        speed = 100f
        motionAngle = 180f
    }

    override fun act(dt: Float) {
        super.act(dt)
        applyPhysics(dt)

        // remove after moving past left edge of screen
        if (x + width < 0)
            remove()
    }
}