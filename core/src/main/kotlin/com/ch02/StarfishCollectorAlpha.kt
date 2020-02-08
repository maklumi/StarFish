package com.ch02


import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle

class StarfishCollectorAlpha : Game() {
    private val batch: SpriteBatch by lazy { SpriteBatch() }

    private val turtleTexture: Texture by lazy { Texture(Gdx.files.internal("ch02/turtle-1.png")) }
    private var turtleX = 0f
    private var turtleY = 0f
    private lateinit var turtleRectangle: Rectangle

    private lateinit var starfishTexture: Texture
    private var starfishX = 0f
    private var starfishY = 0f
    private lateinit var starfishRectangle: Rectangle

    private lateinit var oceanTexture: Texture
    private lateinit var winMessageTexture: Texture

    private var win = false

    override fun create() {
        turtleX = 20f
        turtleY = 20f
        turtleRectangle = Rectangle(turtleX, turtleY,
                                    turtleTexture.width.toFloat(), turtleTexture.height.toFloat())

        starfishTexture = Texture(Gdx.files.internal("ch02/starfish.png"))
        starfishX = 380f
        starfishY = 380f
        starfishRectangle = Rectangle(starfishX, starfishY,
                                      starfishTexture.width.toFloat(), starfishTexture.height.toFloat())

        oceanTexture = Texture(Gdx.files.internal("ch02/water.jpg"))

        winMessageTexture = Texture(Gdx.files.internal("ch02/you-win.png"))

    }

    override fun render() {
        // check user input
        if (Gdx.input.isKeyPressed(Keys.LEFT))
            turtleX--
        if (Gdx.input.isKeyPressed(Keys.RIGHT))
            turtleX++
        if (Gdx.input.isKeyPressed(Keys.UP))
            turtleY++
        if (Gdx.input.isKeyPressed(Keys.DOWN))
            turtleY--

        // update turtle rectangle location
        turtleRectangle.setPosition(turtleX, turtleY)

        // check win condition: turtle must be overlapping starfish
        if (turtleRectangle.overlaps(starfishRectangle)) win = true

        // clear screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // draw graphics
        with(batch) {
            begin()
            draw(oceanTexture, 0f, 0f)
            if (!win) draw(starfishTexture, starfishX, starfishY)
            draw(turtleTexture, turtleX, turtleY)
            if (win) draw(winMessageTexture, 180f, 180f)
            end()
        }

    }
}