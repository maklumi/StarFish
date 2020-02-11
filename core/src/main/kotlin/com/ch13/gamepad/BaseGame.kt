package com.ch13.gamepad

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable


/**
 * Created when program is launched;
 * manages the screens that appear during the game.
 */
abstract class BaseGame : Game() {
    /**
     * Called when game is initialized; stores global reference to game object.
     */

    /**
     * Called when game is initialized,
     * Gdx.input initialized.
     */
    override fun create() {
        // prepare for multiple classes/stages/actors to receive discrete input
        val im = InputMultiplexer()
        Gdx.input.inputProcessor = im

        // parameters for generating a custom bitmap font
        val fontGenerator = FreeTypeFontGenerator(Gdx.files.internal("ch03/OpenSans.ttf"))
        val fontParameters = FreeTypeFontGenerator.FreeTypeFontParameter()
        fontParameters.size = 36
        fontParameters.color = Color.WHITE
        fontParameters.borderWidth = 4f
        fontParameters.borderColor = Color.ORANGE
        fontParameters.borderStraight = true
        fontParameters.minFilter = Texture.TextureFilter.Linear
        fontParameters.magFilter = Texture.TextureFilter.Linear

        val customFont = fontGenerator.generateFont(fontParameters)
        labelStyle = Label.LabelStyle()
        labelStyle.font = customFont
//        labelStyle.font = BitmapFont(Gdx.files.internal("ch03/cooper.fnt"))

        val buttonTex = Texture(Gdx.files.internal("ch03/button.png"))
        val buttonPatch = NinePatch(buttonTex, 24, 24, 24, 24) // left-right-top-bottom slices
        textButtonStyle = TextButton.TextButtonStyle()
        textButtonStyle.up = NinePatchDrawable(buttonPatch)
        textButtonStyle.font = customFont
        textButtonStyle.fontColor = Color.GRAY

        game = this
    }

    companion object {
        /**
         * Stores reference to game; used when calling setActiveScreen method.
         */
        private lateinit var game: BaseGame

        lateinit var labelStyle: Label.LabelStyle // BitmapFont + Color

        lateinit var textButtonStyle: TextButton.TextButtonStyle // NPD + BitmapFont + Color

        /**
         * Used to switch screens while game is running.
         * Method is static to simplify usage.
         */
        fun setActiveScreen(s: BaseGamepadScreen) {
            game.setScreen(s)
        }
    }


}