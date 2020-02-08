package com.ch03

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys


class MenuScreen : BaseScreen() {

    override fun initialize() {
        val ocean = BaseActor(0f, 0f, mainStage)
        ocean.loadTexture("ch03/water.jpg")
        ocean.setSize(800f, 600f)

        val title = BaseActor(0f, 0f, mainStage)
        title.loadTexture("ch03/starfish-collector.png")
        title.centerAtPosition(400f, 300f)
        title.moveBy(0f, 100f)

        val start = BaseActor(0f, 0f, mainStage)
        start.loadTexture("ch03/message-start.png")
        start.centerAtPosition(400f, 300f)
        start.moveBy(0f, -100f)
    }

    override fun update(dt: Float) {
        if (Gdx.input.isKeyPressed(Keys.S)) BaseGame.setActiveScreen(LevelScreen())
    }
}