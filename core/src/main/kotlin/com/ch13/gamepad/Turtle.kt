package com.ch13.gamepad

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Array
import com.ch06.starfish.BaseActor

class Turtle(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {

    init {
        // texture
        val filenames = Array<String>()
        filenames.addAll("ch03/turtle-1.png", "ch03/turtle-2.png", "ch03/turtle-3.png",
                         "ch03/turtle-4.png", "ch03/turtle-5.png", "ch03/turtle-6.png")
        loadAnimationFromFiles(filenames, 0.1f, true)
        // physics
        setAcceleration(400f)
        setMaxSpeed(100f)
        setDeceleration(400f)
        // boundary
        setBoundaryPolygon(8)
    }

    override fun act(dt: Float) {
        super.act(dt)

        if (Controllers.getControllers().size > 0) {
            val gamepad = Controllers.getControllers()[0]
            val xAxis = gamepad.getAxis(XBoxGamepad.AXIS_LEFT_X)
            val yAxis = -gamepad.getAxis(XBoxGamepad.AXIS_LEFT_Y)
            val direction = Vector2(xAxis, yAxis)
            val length = direction.len()
            val deadZone = 0.10f
            if (length > deadZone) {
                speed = length * 100
                motionAngle = direction.angle()
            }
        } else {
            if (Gdx.input.isKeyPressed(Keys.LEFT))
                accelerateAtAngle(180f)
            if (Gdx.input.isKeyPressed(Keys.RIGHT))
                accelerateAtAngle(0f)
            if (Gdx.input.isKeyPressed(Keys.UP))
                accelerateAtAngle(90f)
            if (Gdx.input.isKeyPressed(Keys.DOWN))
                accelerateAtAngle(270f)
        }
        // move
        applyPhysics(dt)
        // pause animation if not moving
        setAnimationPaused(!isMoving)
        // face direction of moving
        if (isMoving) rotation = motionAngle

        boundToWorld()

        alignCamera()
    }
}