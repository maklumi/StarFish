package com.ch14

import com.BaseActor
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Array

class Hero(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    var north: Animation<TextureRegion>
    var south: Animation<TextureRegion>
    var east: Animation<TextureRegion>
    var west: Animation<TextureRegion>

    override fun act(dt: Float) {
        super.act(dt)
        // hero movement controls
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) accelerateAtAngle(180f)
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) accelerateAtAngle(0f)
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) accelerateAtAngle(90f)
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) accelerateAtAngle(270f)
        // pause animation when character not moving
        if (speed == 0f) {
            setAnimationPaused(true)
        } else {
            setAnimationPaused(false)
            // set direction animation
            val angle: Float = motionAngle
            if (angle in 45.0..135.0) setAnimation(north) else if (angle > 135 && angle < 225) setAnimation(west) else if (angle >= 225 && angle <= 315) setAnimation(south) else setAnimation(east)
        }
        applyPhysics(dt)
    }

    init {
        val fileName = "assets/ch14/hero.png"
        val rows = 4
        val cols = 3
        val texture = Texture(Gdx.files.internal(fileName), true)
        val frameWidth = texture.width / cols
        val frameHeight = texture.height / rows
        val frameDuration = 0.2f
        val temp = TextureRegion.split(texture, frameWidth, frameHeight)
        val textureArray = Array<TextureRegion>()
        for (c in 0 until cols) textureArray.add(temp[0][c])
        south = Animation(frameDuration, textureArray, Animation.PlayMode.LOOP_PINGPONG)
        textureArray.clear()
        for (c in 0 until cols) textureArray.add(temp[1][c])
        west = Animation(frameDuration, textureArray, Animation.PlayMode.LOOP_PINGPONG)
        textureArray.clear()
        for (c in 0 until cols) textureArray.add(temp[2][c])
        east = Animation(frameDuration, textureArray, Animation.PlayMode.LOOP_PINGPONG)
        textureArray.clear()
        for (c in 0 until cols) textureArray.add(temp[3][c])
        north = Animation(frameDuration, textureArray, Animation.PlayMode.LOOP_PINGPONG)
        setAnimation(south)
        // set after animation established
        setBoundaryPolygon(8)
        acceleration = 800f
        maxSpeed = 100f
        deceleration = 800f
    }
}