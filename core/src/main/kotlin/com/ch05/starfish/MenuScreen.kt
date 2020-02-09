package com.ch05.starfish

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.EventListener
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.TextButton


class MenuScreen : BaseScreen() {

    override fun initialize() {
        val ocean = BaseActor(0f, 0f, mainStage)
        ocean.loadTexture("ch03/water.jpg")
        ocean.setSize(800f, 600f)

        val title = BaseActor(0f, 0f, mainStage)
        title.loadTexture("ch03/starfish-collector.png")

        val startButton = TextButton("Start", BaseGame.textButtonStyle)
        uiStage.addActor(startButton)

        startButton.addListener(object : EventListener {
            override fun handle(event: Event): Boolean {
                if (event !is InputEvent) return false
                if (event.type != InputEvent.Type.touchDown) return false
                BaseGame.setActiveScreen(LevelScreen())
                return true
            }
        })

        val quitButton = TextButton("Quit", BaseGame.textButtonStyle)
        uiStage.addActor(quitButton)

        quitButton.addListener quit@{ e: Event ->
            if (e !is InputEvent)
                return@quit false

            if (e.type != InputEvent.Type.touchDown)
                return@quit false

            Gdx.app.exit()
            true
        }

        uiTable.add(title).colspan(2)
        uiTable.row()
        uiTable.add(startButton)
        uiTable.add(quitButton)
        uiTable.debug()
    }

    override fun update(dt: Float) {
    }

    override fun keyDown(keycode: Int): Boolean {
        if (Gdx.input.isKeyPressed(Keys.ENTER)) {
//            BaseGame.setActiveScreen(LevelScreen())
            BaseGame.setActiveScreen(StoryScreen())
        }

        if (Gdx.input.isKeyPressed(Keys.ESCAPE))
            Gdx.app.exit()
        return false
    }

}