package com.ch12.zelda

import com.BaseActor
import com.badlogic.gdx.scenes.scene2d.Stage

class ShopHeart(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadTexture("assets/ch12/zelda/heart-icon.png")
    }
}