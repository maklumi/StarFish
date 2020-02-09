package com.ch04

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer


/**
 * Created when program is launched;
 * manages the screens that appear during the game.
 */
abstract class BaseGame : Game() {
    /**
     * Called when game is initialized; stores global reference to game object.
     */
    init {
        @Suppress("LeakingThis")
        game = this
    }

    /**
     * Called when game is initialized,
     * Gdx.input initialized.
     */
    override fun create() {
        // prepare for multiple classes/stages/actors to receive discrete input
        val im = InputMultiplexer()
        Gdx.input.inputProcessor = im
    }

    companion object {
        /**
         * Stores reference to game; used when calling setActiveScreen method.
         */
        private lateinit var game: BaseGame

        /**
         * Used to switch screens while game is running.
         * Method is static to simplify usage.
         */
        fun setActiveScreen(s: BaseScreen) {
            game.setScreen(s)
        }
    }


}