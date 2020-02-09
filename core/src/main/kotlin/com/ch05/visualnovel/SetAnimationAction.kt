package com.ch05.visualnovel

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Action


class SetAnimationAction(private val animationToDisplay: Animation<TextureRegion>) : Action() {

    override fun act(dt: Float): Boolean {
        val ba = target as BaseActor
        ba.setAnimation(animationToDisplay)
        return true // action completed
    }

}