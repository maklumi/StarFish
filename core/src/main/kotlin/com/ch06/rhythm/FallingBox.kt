package com.ch06.rhythm

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions


class FallingBox(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadTexture("ch06/rhythm/box.png")
        setScale(0.75f, 0.75f)
    }

    override fun act(dt: Float) {
        super.act(dt)
        applyPhysics(dt)
    }

    fun flashOut() {
        val duration = 0.25f
        val flashOut = Actions.parallel(
                Actions.scaleTo(1.5f, 1.5f, duration),
                Actions.color(Color.WHITE, duration),
                Actions.fadeOut(duration))

        addAction(flashOut)
        addAction(Actions.after(Actions.removeActor()))
    }
}