package com.ch12.zelda

import com.BaseActor
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Array

class Hero(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    private val north: Animation<TextureRegion>
    private val south: Animation<TextureRegion>
    private val east: Animation<TextureRegion>
    private val west: Animation<TextureRegion>

    var facingAngle: Float

    init {
        val fileName = "assets/ch12/zelda/hero.png"
        val rows = 4
        val cols = 4
        val texture = Texture(Gdx.files.internal(fileName), true)
        val frameWidth = texture.width / cols
        val frameHeight = texture.height / rows
        val frameDuration = 0.2f

        val temp = TextureRegion.split(texture, frameWidth, frameHeight)

        val textureArray = Array<TextureRegion>()
        for (c in 0 until cols)
            textureArray.add(temp[0][c])
        south = Animation(frameDuration, textureArray, Animation.PlayMode.LOOP_PINGPONG)

        textureArray.clear()
        for (c in 0 until cols)
            textureArray.add(temp[1][c])
        west = Animation(frameDuration, textureArray, Animation.PlayMode.LOOP_PINGPONG)

        textureArray.clear()
        for (c in 0 until cols)
            textureArray.add(temp[2][c])
        east = Animation(frameDuration, textureArray, Animation.PlayMode.LOOP_PINGPONG)

        textureArray.clear()
        for (c in 0 until cols)
            textureArray.add(temp[3][c])
        north = Animation(frameDuration, textureArray, Animation.PlayMode.LOOP_PINGPONG)

        setAnimation(south)
        facingAngle = 270f

        // set after animation established
        setBoundaryPolygon(8)
        acceleration = 400f
        maxSpeed = 100f
        deceleration = 400f
    }

    override fun act(dt: Float) {
        super.act(dt)

        // pause animation when character not moving
        if (speed == 0f)
            setAnimationPaused(true)
        else {
            setAnimationPaused(false)

            // set direction animation
            val angle = motionAngle
            if (angle in 45.0..135.0) {
                facingAngle = 90f
                setAnimation(north)
            } else if (angle > 135 && angle < 225) {
                facingAngle = 180f
                setAnimation(west)
            } else if (angle in 225.0..315.0) {
                facingAngle = 270f
                setAnimation(south)
            } else {
                facingAngle = 0f
                setAnimation(east)
            }
        }

        alignCamera()
        boundToWorld()
        applyPhysics(dt)
    }

}