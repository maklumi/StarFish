package com.ch03

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions


class Starfish(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    var collected: Boolean = false

    init {
        loadTexture("ch03/starfish.png")
        setBoundaryPolygon(8)

        val spin = Actions.rotateBy(30f, 1f)
        this.addAction(Actions.forever(spin))
    }
}