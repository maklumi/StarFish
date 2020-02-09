package com.ch05.visualnovel

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Stage


class Background(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {

    var hallway: Animation<TextureRegion> = loadTexture("ch05/homework/bg-hallway.jpg")
    var classroom = loadTexture("ch05/homework/bg-classroom.jpg")
    var scienceLab = loadTexture("ch05/homework/bg-science-lab.jpg")
    var library = loadTexture("ch05/homework/bg-library.jpg")

    init {
        setSize(800f, 600f)
    }

}