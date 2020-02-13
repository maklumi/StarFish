package com.ch16

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.ch16.BaseActor3D.Companion.getList


class LevelScreen : BaseScreen() {
    lateinit var turtle: Turtle
    private lateinit var starfishLabel: Label
    private lateinit var messageLabel: Label

    override fun initialize() {
        val floor = Box(0f, 0f, 0f, mainStage3D)
        floor.loadTexture("assets/ch16/water.jpg")
        floor.setScale(500f, 0.1f, 500f)
        val skydome = Sphere(0f, 0f, 0f, mainStage3D)
        skydome.loadTexture("assets/ch16/sky-sphere.png")
        // when scaling, the negative z-value inverts the sphere,
        //   so that the texture is rendered on the inside
        skydome.setScale(500f, 500f, -500f)
        turtle = Turtle(0f, 0f, 15f, mainStage3D)
        turtle.turnAngle = 90f
        Rock(-15f, 1f, 0f, mainStage3D)
        Rock(-15f, 1f, 15f, mainStage3D)
        Rock(-15f, 1f, 30f, mainStage3D)
        Rock(0f, 1f, 0f, mainStage3D)
        Rock(0f, 1f, 30f, mainStage3D)
        Rock(15f, 1f, 0f, mainStage3D)
        Rock(15f, 1f, 15f, mainStage3D)
        Rock(15f, 1f, 30f, mainStage3D)
        Starfish(10f, 0f, 10f, mainStage3D)
        Starfish(10f, 0f, 20f, mainStage3D)
        Starfish(-10f, 0f, 10f, mainStage3D)
        Starfish(-10f, 0f, 20f, mainStage3D)
        mainStage3D.setCameraPosition(0f, 10f, 0f)
        mainStage3D.setCameraDirection(Vector3(0f, 0f, 0f))
        starfishLabel = Label("Starfish left: 4", BaseGame.labelStyle)
        starfishLabel.color = Color.CYAN
        messageLabel = Label("You Win!", BaseGame.labelStyle)
        messageLabel.color = Color.LIME
        messageLabel.setFontScale(2f)
        messageLabel.isVisible = false
        uiTable.pad(20f)
        uiTable.add(starfishLabel)
        uiTable.row()
        uiTable.add(messageLabel).expandY()
    }

    override fun update(dt: Float) {
        val speed = 3.0f
        val rotateSpeed = 45.0f
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) turtle.moveForward(speed * dt)
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) turtle.turnBy(-rotateSpeed * dt)
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) turtle.turnBy(rotateSpeed * dt)
        mainStage3D.setCameraDirection(turtle.position)
        for (rock in getList(mainStage3D, Rock::class)) turtle.preventOverlap(rock)
        for (starfish in getList(mainStage3D, Starfish::class)) if (turtle.overlaps(starfish)) starfish.remove()
        val starfishCount = BaseActor3D.count(mainStage3D, Starfish::class)
        starfishLabel.setText("Starfish left: $starfishCount")
        if (starfishCount == 0) messageLabel.isVisible = true
    }
}