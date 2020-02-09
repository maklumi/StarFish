package com.ch05.visualnovel

import com.ch05.starfish.cutscene.SetTextAction

/**
 * Designed for use in concert with
 * the DialogBox, SceneSegment, and Scene classes.
 */
class TypewriterAction(text: String) : SetTextAction(text) {

    private var elapsedTime = 0f
    private val charactersPerSecond = 30f

    override fun act(dt: Float): Boolean {
        elapsedTime += dt
        var numberOfCharacters = (elapsedTime * charactersPerSecond).toInt()
        if (numberOfCharacters > textToDisplay.length)
            numberOfCharacters = textToDisplay.length
        val partialText = textToDisplay.substring(0, numberOfCharacters)
        val db = target as DialogBox
        db.setText(partialText)
        // action is complete when all characters have been displayed
        return numberOfCharacters >= textToDisplay.length
    }

}