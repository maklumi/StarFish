package com.ch03

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage

abstract class GameBeta : Game() {
    protected lateinit var mainStage: Stage
    protected lateinit var uiStage: Stage

    override fun create() {
        mainStage = Stage()
        uiStage = Stage()
        initialize()
    }

    abstract fun initialize()

    // Game loop stages:
    // (1) process input
    // (2) update state of game world
    // (3) render the graphics
    override fun render() {
        val dt = Gdx.graphics.deltaTime

        // act method
        mainStage.act(dt)
        uiStage.act(dt)

        // defined by user
        update(dt)

        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // draw the graphics
        mainStage.draw()
        uiStage.draw()
    }

    abstract fun update(dt: Float)
}