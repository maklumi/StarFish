package com.ch11.mariobros

import com.BaseActor
import com.badlogic.gdx.scenes.scene2d.Stage

class Flag(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadAnimationFromSheet("assets/ch11/mario/items/flag.png", 1, 2, 0.2f, true)
    }
}