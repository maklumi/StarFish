package com.ch11.mariobros

import com.BaseActor
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Array
import kotlin.math.abs


class Koala(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    private val stand = loadTexture("assets/ch11/mario/koala/stand.png")
    private val walk: Animation<TextureRegion>

    private val walkAcceleration = 200f
    private val walkDeceleration = 200f
    private val maxHorizontalSpeed = 100f
    private val gravity = 700f
    private val maxVerticalSpeed = 1000f

    private val jump: Animation<TextureRegion>
    private val jumpSpeed: Float
    private val belowSensor: BaseActor

    init {

        val walkFileNames = Array<String>()
        walkFileNames.addAll("assets/ch11/mario/koala/walk-1.png", "assets/ch11/mario/koala/walk-2.png",
                             "assets/ch11/mario/koala/walk-3.png", "assets/ch11/mario/koala/walk-2.png")
        walk = loadAnimationFromFiles(walkFileNames, 0.2f, true)

        setBoundaryPolygon(8)

        jump = loadTexture("assets/ch11/mario/koala/jump.png")
        jumpSpeed = 450f
        // set up the below sensor
        belowSensor = BaseActor(0f, 0f, s)
        belowSensor.loadTexture("assets/ch11/mario/white.png")
        belowSensor.setSize(this.width - 8, 8f)
        belowSensor.setBoundaryRectangle()
        belowSensor.isVisible = true
    }

    override fun act(dt: Float) {
        super.act(dt)

        // get keyboard input
        if (Gdx.input.isKeyPressed(Keys.LEFT))
            accelerationVec.add(-walkAcceleration, 0f)

        if (Gdx.input.isKeyPressed(Keys.RIGHT))
            accelerationVec.add(walkAcceleration, 0f)

        // decelerate when not accelerating
        if (!Gdx.input.isKeyPressed(Keys.RIGHT) && !Gdx.input.isKeyPressed(Keys.LEFT)) {
            val decelerationAmount = walkDeceleration * dt

            val walkDirection = if (velocityVec.x > 0) 1f else -1f

            var walkSpeed = abs(velocityVec.x)

            walkSpeed -= decelerationAmount

            if (walkSpeed < 0f) walkSpeed = 0f

            velocityVec.x = walkSpeed * walkDirection
        }

        // apply gravity
        accelerationVec.add(0f, -gravity)

        velocityVec.add(accelerationVec.x * dt, accelerationVec.y * dt)

        velocityVec.x = MathUtils.clamp(velocityVec.x, -maxHorizontalSpeed, maxHorizontalSpeed)
        velocityVec.y = MathUtils.clamp(velocityVec.y, -maxVerticalSpeed, maxVerticalSpeed)

        moveBy(velocityVec.x * dt, velocityVec.y * dt)

        // reset acceleration
        accelerationVec.set(0f, 0f)

        // move the below sensor below the koala
        belowSensor.setPosition(x + 4, y - 8)

        // manage animations
        if (isOnSolid()) {
            belowSensor.color = Color.GREEN
            if (velocityVec.x in -0.01f..0.01f)
                setAnimation(stand)
            else
                setAnimation(walk)
        } else {
            belowSensor.color = Color.RED
            setAnimation(jump)
        }

        if (velocityVec.x > 0.01f) // face right
            scaleX = 1f

        if (velocityVec.x < 0f) // face left
            scaleX = -1f

        alignCamera()
        boundToWorld()
    }

    fun belowOverlaps(actor: BaseActor): Boolean {
        return belowSensor.overlaps(actor)
    }

    fun isOnSolid(): Boolean {

        for (actor in getList(stage, Solid::class)) {
            val solid = actor as Solid
            if (belowOverlaps(solid) && solid.isEnabled)
                return true
        }

        return false
    }


    fun jump() {
        velocityVec.y = jumpSpeed
    }

    fun isFalling(): Boolean {
        return velocityVec.y < 0
    }

    fun spring() {
        velocityVec.y = 1.5f * jumpSpeed
    }

    fun isJumping(): Boolean {
        return velocityVec.y > 0f
    }
}
