package com.ch12.zelda

import com.BaseActor
import com.badlogic.gdx.scenes.scene2d.Stage

open class Solid(x: Float, y: Float, width: Float, height: Float, s: Stage) : BaseActor(x, y, s) {

    init {
        this.setSize(width, height)
        setBoundaryRectangle()
    }

    var isEnabled: Boolean = true
}