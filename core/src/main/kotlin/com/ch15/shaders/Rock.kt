package com.ch15.shaders

import com.badlogic.gdx.scenes.scene2d.Stage

class Rock(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {

    init {
        loadTexture("ch03/rock.png")
        setBoundaryPolygon(8)
    }

}