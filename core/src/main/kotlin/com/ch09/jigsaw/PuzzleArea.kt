package com.ch09.jigsaw

import com.badlogic.gdx.scenes.scene2d.Stage

class PuzzleArea(x: Float, y: Float, s: Stage) : DropTargetActor(x, y, s) {
    var row: Int = 0
    var col: Int = 0

    init {
        loadTexture("assets/ch09/jigsaw/border.jpg")
    }
}