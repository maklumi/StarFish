package com.ch12.zelda

import com.BaseActor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions

class Smoke(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadTexture("assets/ch12/zelda/smoke.png")
        addAction(Actions.fadeOut(0.5f))
        addAction(Actions.after(Actions.removeActor()))
    }
}