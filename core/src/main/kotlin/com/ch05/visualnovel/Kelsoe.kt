package com.ch05.visualnovel

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Stage


class Kelsoe(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {

    val normal: Animation<TextureRegion> = loadTexture("ch05/homework/kelsoe-normal.png")
    val sad = loadTexture("ch05/homework/kelsoe-sad.png")
    val lookLeft = loadTexture("ch05/homework/kelsoe-look-left.png")
    val lookRight = loadTexture("ch05/homework/kelsoe-look-right.png")

}