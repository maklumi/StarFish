package com.ch16

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table

abstract class BaseScreen : Screen, InputProcessor {
    @JvmField
    protected var mainStage3D: Stage3D = Stage3D()
    protected var uiStage: Stage = Stage()
    @JvmField
    protected var uiTable: Table = Table()

    abstract fun initialize()
    abstract fun update(dt: Float)
    // this is the gameloop. update, then render.
    override fun render(delta: Float) {
        // limit amount of time that can pass while window is being dragged
        var dt = delta
        dt = dt.coerceAtMost(1 / 30f)
        // act methods
        uiStage.act(dt)
        mainStage3D.act(dt)
        // defined by user
        update(dt)
        // render
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT + GL20.GL_DEPTH_BUFFER_BIT)
        // draw the graphics
        mainStage3D.draw()
        uiStage.draw()
    }

    // methods required by Screen interface
    override fun resize(width: Int, height: Int) {
        uiStage.viewport.update(width, height, true)
    }

    override fun pause() {}
    override fun resume() {}
    override fun dispose() {}
    /**
     * Called when this becomes the active screen in a Game.
     * Set up InputMultiplexer here, in case screen is reactivated at a later time.
     */
    override fun show() {
        val im = Gdx.input.inputProcessor as InputMultiplexer
        im.addProcessor(this)
        im.addProcessor(uiStage)
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
    }

    // methods required by InputProcessor interface
    override fun keyDown(keycode: Int): Boolean {
        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        return false
    }

    override fun keyTyped(c: Char): Boolean {
        return false
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return false
    }

    override fun scrolled(amount: Int): Boolean {
        return false
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return false
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    init {
        uiTable.setFillParent(true)
        uiStage.addActor(uiTable)
        this.initialize()
    }
}