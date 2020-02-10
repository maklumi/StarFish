package com.ch06.rhythm

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table

abstract class BaseScreen : Screen, InputProcessor {

    protected val mainStage: Stage = Stage()
    protected val uiStage: Stage = Stage()
    protected val uiTable: Table = Table()

    init {
        uiTable.setFillParent(true)
        uiStage.addActor(uiTable)

        this.initialize()
    }

    abstract fun initialize()
    abstract fun update(dt: Float)

    // Gameloop:
// (1) process input (discrete handled by listener; continuous in update)
// (2) update game logic
// (3) render the graphics

    override fun render(dt: Float) {
        // act methods
        uiStage.act(dt)
        mainStage.act(dt)
        // defined by user
        update(dt)
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        // draw the graphics
        mainStage.draw()
        uiStage.draw()
    }

    // methods required by Screen interface
    override fun resize(width: Int, height: Int) {}

    override fun pause() {}
    override fun resume() {}
    override fun dispose() {
        uiTable.clear()
        uiStage.dispose()
        mainStage.dispose()
    }


    /**
     * Called when this becomes the active screen in a Game.
     * Set up InputMultiplexer here, in case screen is reactivated at a later time.
     */
    override fun show() {
        val im = Gdx.input.inputProcessor as InputMultiplexer
        im.addProcessor(this)
        im.addProcessor(uiStage)
        im.addProcessor(mainStage)
    }

    /**
     * Called when this is no longer the active screen in a Game.
     * Screen class and Stages no longer process input.
     * Other InputProcessors must be removed manually.
     */
    override fun hide() {
        val im = Gdx.input.inputProcessor as InputMultiplexer
        im.removeProcessor(this)
        im.removeProcessor(uiStage)
        im.removeProcessor(mainStage)
    }

    /**
     * Useful for checking for touch-down events.
     */
    fun isTouchDownEvent(e: Event): Boolean {
        return e is InputEvent && e.type == InputEvent.Type.touchDown
    }

    // methods required by InputProcessor interface
    override fun keyDown(keycode: Int): Boolean = false

    override fun keyUp(keycode: Int): Boolean = false

    override fun keyTyped(c: Char): Boolean = false

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean = false

    override fun scrolled(amount: Int): Boolean = false

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = false

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false


}