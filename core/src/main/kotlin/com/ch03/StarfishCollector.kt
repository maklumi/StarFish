package com.ch03

import com.badlogic.gdx.scenes.scene2d.actions.Actions


class StarfishCollector : GameBeta() {
    private lateinit var turtle: Turtle
    private var win: Boolean = false

    override fun initialize() {
        val ocean = BaseActor(0f, 0f, mainStage)
        ocean.loadTexture("ch03/water-border.jpg")
        ocean.setSize(1200f, 900f)
        BaseActor.setWorldBounds(ocean)

        Starfish(400f, 400f, mainStage)
        Starfish(500f, 100f, mainStage)
        Starfish(100f, 450f, mainStage)
        Starfish(200f, 250f, mainStage)

        Rock(200f, 150f, mainStage)
        Rock(100f, 300f, mainStage)
        Rock(300f, 350f, mainStage)
        Rock(450f, 200f, mainStage)

        turtle = Turtle(20f, 20f, mainStage)
        turtle.debug = true
    }

    override fun update(dt: Float) {
        for (rockActor in BaseActor.getList(mainStage, "Rock")) { // Rock::class.java.name
            rockActor.preventOverlap(turtle)    // rock moves away when overlap
//            turtle.preventOverlap(rockActor)    // rock solid stays still
        }

        for (starfishActor in BaseActor.getList(mainStage, "Starfish")) {
            val starfish = starfishActor as Starfish
            if (turtle.overlaps(starfish) && !starfish.collected) {
                starfish.collected = true
                starfish.clearActions()
                starfish.addAction(Actions.fadeOut(1f))
                starfish.addAction(Actions.after(Actions.removeActor()))

                val whirl = Whirlpool(0f, 0f, mainStage)
                whirl.centerAtActor(starfish)
                whirl.setOpacity(0.25f)
            }
        }

        if (BaseActor.count(mainStage, "Starfish") == 0 && !win) {
            win = true
            val youWinMessage = BaseActor(0f, 0f, uiStage)
            youWinMessage.loadTexture("ch03/you-win.png")
            youWinMessage.centerAtPosition(400f, 300f)
            youWinMessage.setOpacity(0f)
            youWinMessage.addAction(Actions.delay(1f))
            youWinMessage.addAction(Actions.after(Actions.fadeIn(1f)))
        }
    }
}