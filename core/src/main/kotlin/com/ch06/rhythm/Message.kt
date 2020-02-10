package com.ch06.rhythm

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions


class Message(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    val perfect = loadTexture("ch06/rhythm/perfect.png")
    val great = loadTexture("ch06/rhythm/great.png")
    val good = loadTexture("ch06/rhythm/good.png")
    val almost = loadTexture("ch06/rhythm/almost.png")
    val miss = loadTexture("ch06/rhythm/miss.png")
    private val countdown3 = loadTexture("ch06/rhythm/countdown-3.png")
    private val countdown2 = loadTexture("ch06/rhythm/countdown-2.png")
    private val countdown1 = loadTexture("ch06/rhythm/countdown-1.png")
    private val countdownGo = loadTexture("ch06/rhythm/countdown-go.png")
    private val congratulations = loadTexture("ch06/rhythm/congratulations.png")

    private val blip = Gdx.audio.newSound(Gdx.files.internal("ch06/rhythm/blip.wav"))
    private val tone = Gdx.audio.newSound(Gdx.files.internal("ch06/rhythm/tone.wav"))

    fun pulseFade() {
        setOpacity(1f)
        clearActions()
        val pulseFade = Actions.sequence(
                Actions.scaleTo(1.1f, 1.1f, 0.05f),
                Actions.scaleTo(1.0f, 1.0f, 0.05f),
                Actions.delay(1f),
                Actions.fadeOut(0.5f))
        addAction(pulseFade)
    }

    fun displayCountdown() {
        val countdown = Actions.sequence(
                Actions.run { setAnimation(countdown3) },
                Actions.run { blip.play() },
                Actions.alpha(1f),
                Actions.scaleTo(1.1f, 1.1f, 0.05f), Actions.scaleTo(1.0f, 1.0f, 0.05f),
                Actions.delay(0.5f), Actions.fadeOut(0.4f),
                Actions.run { setAnimation(countdown2) },
                Actions.run { blip.play() },
                Actions.alpha(1f),
                Actions.scaleTo(1.1f, 1.1f, 0.05f), Actions.scaleTo(1.0f, 1.0f, 0.05f),
                Actions.delay(0.5f), Actions.fadeOut(0.4f),
                Actions.run { setAnimation(countdown1) },
                Actions.run { blip.play() },
                Actions.alpha(1f),
                Actions.scaleTo(1.1f, 1.1f, 0.05f), Actions.scaleTo(1.0f, 1.0f, 0.05f),
                Actions.delay(0.5f), Actions.fadeOut(0.4f),
                Actions.run { setAnimation(countdownGo) },
                Actions.run { tone.play() },
                Actions.alpha(1f),
                Actions.fadeOut(1f))

        addAction(countdown)
    }

    fun displayCongratulations() {
        setOpacity(0f)
        setAnimation(congratulations)
        setScale(2f)
        addAction(Actions.fadeIn(4f))
    }
}