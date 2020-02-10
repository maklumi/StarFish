package com.ch07.sidescrolling.effects

import com.badlogic.gdx.scenes.scene2d.Stage
import com.BaseActor

class Explosion(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadAnimationFromSheet("assets/ch07/plane/explosion.png", 6, 6, 0.02f, false)
    }

    override fun act(dt: Float) {
        super.act(dt)

        if (isAnimationFinished())
            remove()
    }
}