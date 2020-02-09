package com.ch05.starfish.cutscene

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Action

class SceneSegment(private val actor: Actor, private val action: Action) {

    val isFinished: Boolean
        get() = actor.actions.size == 0

    fun start() {
        actor.clearActions()
        actor.addAction(action)
    }

    /**
     * End this segment early
     */
    fun finish() {
        // simulate 100000 seconds elapsed time to complete in-progress action
        if (actor.hasActions())
            actor.actions.first().act(100000f)

        actor.clearActions()
    }
}