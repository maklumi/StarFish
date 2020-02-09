package com.ch05.starfish

import com.badlogic.gdx.scenes.scene2d.Stage

class Sign(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    // the text to be displayed
    var text: String = ""
    // used to determine if sign text is currently being displayed
    var isViewing: Boolean = false

    init {
        loadTexture("ch03/sign.png")
    }
}