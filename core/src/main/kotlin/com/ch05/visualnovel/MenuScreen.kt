package com.ch05.visualnovel

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.ch05.starfish.BaseActor

class MenuScreen: BaseScreen(){

    override fun initialize() {
        val notebook = BaseActor(0f, 0f, mainStage)
        notebook.loadTexture("ch05/homework/notebook.jpg")
        notebook.setSize(800f, 600f)

        val title = BaseActor(0f, 0f, mainStage)
        title.loadTexture("ch05/homework/missing-homework.png")

        val startButton = TextButton("Start", BaseGame.textButtonStyle)

        startButton.addListener startButton@{ e: Event ->
            if (e !is InputEvent || e.type != Type.touchDown)
                return@startButton false

            BaseGame.setActiveScreen(StoryScreen())
            false
        }

        val quitButton = TextButton("Quit", BaseGame.textButtonStyle)
        quitButton.addListener quitButton@{ e: Event ->
            if (e !is InputEvent || e.type != Type.touchDown)
                return@quitButton false

            Gdx.app.exit()
            false
        }

        uiTable.add(title).colspan(2)
        uiTable.row()
        uiTable.add(startButton)
        uiTable.add(quitButton)
    }

    override fun update(dt: Float) {
    }

    override fun keyDown(keycode: Int): Boolean {
        if (Gdx.input.isKeyPressed(Keys.ENTER))
            BaseGame.setActiveScreen(StoryScreen())

        if (Gdx.input.isKeyPressed(Keys.ESCAPE))
            Gdx.app.exit()

        return false
    }
}