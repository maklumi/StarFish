package com.ch04

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.math.MathUtils


class Spaceship(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {

    private val thrusters = Thrusters(0f, 0f, s)
    private val shield = Shield(0f, 0f, s)
    var shieldPower = 100

    init {
        loadTexture("ch04/spaceship.png")
        setBoundaryPolygon(8)

        setAcceleration(200f)
        setMaxSpeed(100f)
        setDeceleration(10f)

        addActor(thrusters)
        thrusters.setPosition(-thrusters.width, height / 2 - thrusters.height / 2)

        addActor(shield)
        shield.centerAtPosition(width / 2, height / 2)

    }

    fun warp() {
        if (stage == null) return

        val warp1 = Warp(0f, 0f, this.stage)
        warp1.centerAtActor(this)
        setPosition(MathUtils.random(800).toFloat(), MathUtils.random(600).toFloat())
        val warp2 = Warp(0f, 0f, this.stage)
        warp2.centerAtActor(this)
    }

    fun shoot() {
        if (stage == null) return

        val laser = Laser(0f, 0f, this.stage)
        laser.centerAtActor(this)
        laser.rotation = this.rotation
        laser.motionAngle = this.rotation
    }

    override fun act(dt: Float) {
        super.act(dt)

        val degreesPerSecond = 120f // degrees per second
        if (Gdx.input.isKeyPressed(Keys.LEFT)) rotateBy(degreesPerSecond * dt)
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) rotateBy(-degreesPerSecond * dt)
        if (Gdx.input.isKeyPressed(Keys.UP)) {
            accelerateAtAngle(rotation)
            thrusters.isVisible = true
        } else {
            thrusters.isVisible = false
        }

        applyPhysics(dt)

        wrapAroundWorld()

        shield.setOpacity(shieldPower / 100f)
        if (shieldPower <= 0)
            shield.isVisible = false

    }
}