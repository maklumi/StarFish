package com.ch12.zelda

import com.badlogic.gdx.scenes.scene2d.Stage
import com.BaseActor

class Arrow(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadTexture("assets/ch12/zelda/arrow.png")
        speed = 400f
    }

    override fun act(dt: Float) {
        super.act(dt)
        applyPhysics(dt)
    }
}