package com.ch05.visualnovel

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align

class DialogBox(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {

    private val dialogLabel = Label(" ", BaseGame.labelStyle)
    private val padding = 16f

    init {
        loadTexture("ch03/dialog-translucent.png")
        dialogLabel.setWrap(true)
        dialogLabel.setAlignment(Align.topLeft)
        dialogLabel.setPosition(padding, padding)
        setDialogSize(width, height)
        addActor(dialogLabel)
    }

    fun setDialogSize(width: Float, height: Float) {
        setSize(width, height)
        dialogLabel.width = width - 2 * padding
        dialogLabel.height = height - 2 * padding
    }

    fun setText(text: String?) {
        dialogLabel.setText(text)
    }

    fun setFontScale(scale: Float) {
        dialogLabel.setFontScale(scale)
    }

    fun setFontColor(color: Color?) {
        dialogLabel.color = color
    }

    fun setBackgroundColor(color: Color?) {
        this.color = color
    }

    fun alignTopLeft() {
        dialogLabel.setAlignment(Align.topLeft)
    }

    fun alignCenter() {
        dialogLabel.setAlignment(Align.center)
    }

}