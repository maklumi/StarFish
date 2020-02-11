package com.ch14

import com.BaseActor
import com.badlogic.gdx.scenes.scene2d.Stage

class Coin(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadTexture("assets/ch14/coin.png")
        setBoundaryPolygon(6)
    }
}