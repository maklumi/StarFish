package com.ch14

import com.BaseActor
import com.badlogic.gdx.scenes.scene2d.Stage

class Wall(x: Float, y: Float, w: Float, h: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadTexture("assets/ch14/square.jpg")
        setSize(w, h)
        setBoundaryRectangle()
    }
}