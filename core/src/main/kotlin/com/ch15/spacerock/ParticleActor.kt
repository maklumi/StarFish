package com.ch15.spacerock

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group

open class ParticleActor(pfxFile: String, imageDirectory: String) : Group() {
    private val effect = ParticleEffect()
    private val renderingActor: ParticleRenderer

    init {
        val filepath = "$imageDirectory/$pfxFile"
        effect.load(Gdx.files.internal(filepath), Gdx.files.internal(imageDirectory))
        renderingActor = ParticleRenderer(effect)
        this.addActor(renderingActor)
    }

    private inner class ParticleRenderer(val effect: ParticleEffect) : Actor() {
        override fun draw(batch: Batch, parentAlpha: Float) {
            effect.draw(batch)
        }

    }

    fun start() {
        effect.start()
    }

    // pauses continuous emitters
    fun stop() {
        effect.allowCompletion()
    }

    val isRunning: Boolean
        get() = !effect.isComplete

    fun centerAtActor(other: Actor) {
        setPosition(other.x + other.width / 2, other.y + other.height / 2)
    }

    override fun act(dt: Float) {
        super.act(dt)
        effect.update(dt)
        if (effect.isComplete && !effect.emitters.first().isContinuous) {
            effect.dispose()
            this.remove()
        }
    }

}