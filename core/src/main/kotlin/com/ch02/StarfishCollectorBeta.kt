package com.ch02

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture

class StarfishCollectorBeta : GameBeta() {
    private lateinit var turtle: Turtle
    private lateinit var starfish: ActorBeta
    private lateinit var ocean: ActorBeta
    private lateinit var winMessage: ActorBeta

    private var win: Boolean = false

    override fun initialize() {
        ocean = ActorBeta()
        ocean.setTexture(Texture(Gdx.files.internal("ch02/water.jpg")))
        mainStage.addActor(ocean)

        starfish = ActorBeta()
        starfish.setTexture(Texture(Gdx.files.internal("ch02/starfish.png")))
        starfish.setPosition(380f, 380f)
        mainStage.addActor(starfish)

        turtle = Turtle()
        turtle.setTexture(Texture(Gdx.files.internal("ch02/turtle-1.png")))
        turtle.setPosition(20f, 20f)
        mainStage.addActor(turtle)

        winMessage = ActorBeta()
        winMessage.setTexture(Texture(Gdx.files.internal("ch02/you-win.png")))
        winMessage.setPosition(180f, 180f)
        winMessage.isVisible = false
        mainStage.addActor(winMessage)

        win = false
    }

    override fun update(dt: Float) {
        // check win condition: turtle must be overlapping starfish
        if (turtle.overlaps(starfish)) {
            starfish.remove()
            winMessage.isVisible = true
        }
    }
}