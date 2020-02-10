package com.ch11.mariobros

import com.badlogic.gdx.scenes.scene2d.Stage
import com.BaseActor

class Springboard(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadAnimationFromSheet("assets/ch11/mario/items/springboard.png", 1, 3, 0.2f, true)
    }
}