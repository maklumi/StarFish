package com.ch04

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions


class Warp(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {

    init {
        loadAnimationFromSheet("ch04/warp.png", 4, 8, 0.05f, true)

        addAction(Actions.delay(1f))
        addAction(Actions.after(Actions.fadeOut(0.5f)))
        addAction(Actions.after(Actions.removeActor()))
    }

}