package com.ch01

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch

/** [com.badlogic.gdx.ApplicationListener] implementation shared by all platforms.  */
class HelloWorldImage : Game() {
    private lateinit var batch: SpriteBatch
    private lateinit var texture: Texture

    override fun create() {
        batch = SpriteBatch()
        val worldFile = Gdx.files.internal("ch01/world.png")
        texture = Texture(worldFile)
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.begin()
        batch.draw(texture, 192f, 112f)
        batch.end()
    }

    override fun dispose() {
        texture.dispose()
        batch.dispose()
    }

}