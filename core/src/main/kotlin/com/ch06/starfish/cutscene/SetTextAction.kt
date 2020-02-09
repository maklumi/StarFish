package com.ch06.starfish.cutscene

import com.badlogic.gdx.scenes.scene2d.Action;
import com.ch06.starfish.DialogBox


/**
 *  Designed for use in concert with
 *      the DialogBox, SceneSegment, and Scene classes.
 */

open class SetTextAction(protected var textToDisplay: String) : Action() {

    override fun act(dt: Float): Boolean {
        val db = target as DialogBox
        db.setText(textToDisplay)
        return true // action completed
    }

}