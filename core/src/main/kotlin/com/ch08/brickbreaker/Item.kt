package com.ch08.brickbreaker

import com.BaseActor
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions


class Item(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {

    var type: Type? = null
        set(t) {
            field = t

            when (t) {
                Type.PADDLE_EXPAND -> loadTexture("assets/ch08/breakout/items/paddle-expand.png")
                Type.PADDLE_SHRINK -> loadTexture("assets/ch08/breakout/items/paddle-shrink.png")
                Type.BALL_SPEED_UP -> loadTexture("assets/ch08/breakout/items/ball-speed-up.png")
                Type.BALL_SPEED_DOWN -> loadTexture("assets/ch08/breakout/items/ball-speed-down.png")
                else -> loadTexture("assets/ch08/breakout/items/item-blank.png")
            }
        }

    enum class Type {
        PADDLE_EXPAND, PADDLE_SHRINK,
        BALL_SPEED_UP, BALL_SPEED_DOWN
    }

    init {
        setRandomType()

        speed = 100f
        motionAngle = 270f

        setSize(50f, 50f)
        // update related data
        setOrigin(25f, 25f)
        setBoundaryRectangle()

        setScale(0f, 0f)
        addAction(Actions.scaleTo(1f, 1f, 0.25f))
    }

    private fun setRandomType() {
        val randomIndex = MathUtils.random(0, Type.values().size - 1)
        val randomType = Type.values()[randomIndex]
        type = randomType
    }

    override fun act(dt: Float) {
        super.act(dt)
        applyPhysics(dt)

        if (y < -50)
            remove()
    }
}