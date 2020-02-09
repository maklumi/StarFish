package com.ch04

import com.badlogic.gdx.scenes.scene2d.Stage


class Explosion(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {

    init {
        loadAnimationFromSheet("ch04/explosion.png", 6, 6, 0.03f, false)
    }

    override fun act(dt: Float) {
        super.act(dt)

        if (isAnimationFinished())
            remove()
    }
}