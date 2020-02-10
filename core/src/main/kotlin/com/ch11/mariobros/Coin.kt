package com.ch11.mariobros

import com.BaseActor
import com.badlogic.gdx.scenes.scene2d.Stage

class Coin(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadAnimationFromSheet("assets/ch11/mario/items/coin.png", 1, 6, 0.1f, true)
    }
}