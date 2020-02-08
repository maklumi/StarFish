package com.ch02

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.Actor

/**
 * Extend the Actor class to include graphics and collision detection.
 * Actor class stores data such as position and rotation.
 */
open class ActorBeta : Actor() {
    private val textureRegion = TextureRegion()
    private val rectangle = Rectangle()

    fun setTexture(t: Texture) {
        textureRegion.setRegion(t)
        this.setSize(t.width.toFloat(), t.height.toFloat())
        rectangle.setSize(t.width.toFloat(), t.height.toFloat())
    }

    private fun getRectangle(): Rectangle {
        rectangle.setPosition(this.x, this.y)
        return rectangle
    }

    fun overlaps(other: ActorBeta): Boolean {
        return this.getRectangle().overlaps(other.getRectangle())
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        super.draw(batch, parentAlpha)

        val c = color // used to apply tint color effect

        batch.setColor(c.r, c.g, c.b, c.a)

        if (isVisible)
            batch.draw(textureRegion,
                       x, y, originX, originY,
                       width, height, scaleX, scaleY, rotation)
    }
}