package com.ch16

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle

abstract class BaseGame : Game() {
    /**
     * Called when game is initialized,
     * after Gdx.input and other objects have been initialized.
     */
    override fun create() {
        // prepare for multiple classes/stages/actors to receive discrete input
        val im = InputMultiplexer()
        Gdx.input.inputProcessor = im
        // parameters for generating a custom bitmap font
        val fontGenerator = FreeTypeFontGenerator(Gdx.files.internal("assets/ch16/OpenSans.ttf"))
        val fontParameters = FreeTypeFontParameter()
        fontParameters.size = 36
        fontParameters.color = Color.WHITE
        fontParameters.borderWidth = 2f
        fontParameters.borderColor = Color.BLACK
        fontParameters.borderStraight = true
        fontParameters.minFilter = TextureFilter.Linear
        fontParameters.magFilter = TextureFilter.Linear
        val customFont = fontGenerator.generateFont(fontParameters)
        labelStyle = LabelStyle()
        labelStyle.font = customFont

        game = this
    }

    companion object {
        /**
         * Stores reference to game; used when calling setActiveScreen method.
         */
        private lateinit var game: BaseGame
        lateinit var labelStyle: LabelStyle
        /**
         * Used to switch screens while game is running.
         * Method is static to simplify usage.
         */
        @JvmStatic
        fun setActiveScreen(s: BaseScreen?) {
            game.setScreen(s)
        }
    }

}