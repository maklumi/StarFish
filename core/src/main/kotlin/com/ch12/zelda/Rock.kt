package com.ch12.zelda

import com.badlogic.gdx.scenes.scene2d.Stage

class Rock(x: Float, y: Float, s: Stage) : Solid(x, y, 32f, 32f, s) {
    init {
        loadTexture("assets/ch12/zelda/rock.png")
        setBoundaryPolygon(8)
    }
}