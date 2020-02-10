package com.ch08.brickbreaker

import com.BaseActor
import com.badlogic.gdx.scenes.scene2d.Stage

class Paddle(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {

    init {
        loadTexture("assets/ch08/breakout/paddle.png")
    }

}