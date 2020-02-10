package com.ch11.mariobros

import com.BaseActor
import com.badlogic.gdx.scenes.scene2d.Stage

open class Solid(x: Float, y: Float, width: Float, height: Float, s: Stage) : BaseActor(x, y, s) {

    init {
//        loadTexture("assets/mario/empty.png")
        this.setSize(width, height)
        setBoundaryRectangle()
    }

    var isEnabled: Boolean = true
}