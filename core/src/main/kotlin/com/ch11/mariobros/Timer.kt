package com.ch11.mariobros

import com.BaseActor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions

class Timer(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadTexture("assets/ch11/mario/items/timer.png")

        val pulse = Actions.sequence(
                Actions.scaleTo(1.1f, 1.1f, 0.5f),
                Actions.scaleTo(1.0f, 1.0f, 0.5f))

        addAction(Actions.forever(pulse))
    }
}