package com.ch15.shaders

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Array


class Turtle(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {

    private val shaders = arrayOf("blur.fs", "border.fs", "default.fs", "glow-pulse.fs",
                                  "grayscale.fs", "grayscale-pulse.fs", "wave.fs")
    private val one = shaders.random()
    private val vertexShaderCode: String = Gdx.files.internal("assets/ch15/shaders/default.vs").readString()
    private val fragmentShaderCode: String = Gdx.files.internal("assets/ch15/shaders/$one").readString()
    private val shaderProgram: ShaderProgram = ShaderProgram(vertexShaderCode, fragmentShaderCode)
    private var time = 0f

    init {
        println("[Turtle] Using fragment shader code : $one")
        println(fragmentShaderCode)

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

        // to detect errors in GPU compilation
        if (!shaderProgram.isCompiled) println("Shader compile error: " + shaderProgram.log)
    }

    override fun act(dt: Float) {
        super.act(dt)

        if (Gdx.input.isKeyPressed(Keys.LEFT))
            accelerateAtAngle(180f)
        if (Gdx.input.isKeyPressed(Keys.RIGHT))
            accelerateAtAngle(0f)
        if (Gdx.input.isKeyPressed(Keys.UP))
            accelerateAtAngle(90f)
        if (Gdx.input.isKeyPressed(Keys.DOWN))
            accelerateAtAngle(270f)
        // move
        applyPhysics(dt)
        // pause animation if not moving
        setAnimationPaused(!isMoving)
        // face direction of moving
        if (isMoving) rotation = motionAngle

        boundToWorld()

        alignCamera()

        time += dt % 60f
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.shader = shaderProgram

        // note: if variable is not used in shader, it is removed when compiled
        //   and attempting to set its value creates an error.
        if (one in arrayOf("glow-pulse.fs", "grayscale-pulse.fs"))
            shaderProgram.setUniformf("u_time", time)

        if (one in arrayOf("glow-pulse.fs", "blur.fs", "border.fs"))
            shaderProgram.setUniformf("u_imageSize", Vector2(width, height))

        if (one in arrayOf("border.fs")) {
            shaderProgram.setUniformf("u_borderColor", Color.BLACK)
            shaderProgram.setUniformf("u_borderSize", 2f)
        }

        if (one in arrayOf("blur.fs"))
            shaderProgram.setUniformi("u_blurRadius", 5)

        if (one == "glow-pulse.fs")
            shaderProgram.setUniformi("u_glowRadius", 5)

        if (one in arrayOf("wave.fs")) {
            shaderProgram.setUniformf("u_amplitude", Vector2(2f, 3f))
            shaderProgram.setUniformf("u_wavelength", Vector2(17f, 19f))
            shaderProgram.setUniformf("u_velocity", Vector2(10f, 11f))
        }

        super.draw(batch, parentAlpha)
        batch.shader = null
    }
}