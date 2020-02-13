package com.ch16

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color


class DemoScreen : BaseScreen() {

    private lateinit var player: BaseActor3D

    override fun initialize() {
        val screen = Box(0f, 0f, 0f, mainStage3D)
        screen.setScale(16f, 12f, 0.1f)
        screen.loadTexture("assets/ch16/starfish-collector.png")
        val markerO = Box(0f, 0f, 0f, mainStage3D)
        markerO.setColor(Color.BROWN)
        markerO.loadTexture("assets/ch16/crate.jpg")
        val markerX = Box(5f, 0f, 0f, mainStage3D)
        markerX.setColor(Color.RED)
        markerX.loadTexture("assets/ch16/crate.jpg")
        val markerY = Box(0f, 5f, 0f, mainStage3D)
        markerY.setColor(Color.GREEN)
        markerY.loadTexture("assets/ch16/crate.jpg")
        val markerZ = Box(0f, 0f, 5f, mainStage3D)
        markerZ.setColor(Color.BLUE)
        markerZ.loadTexture("assets/ch16/crate.jpg")
        player = Sphere(0f, 1f, 8f, mainStage3D)
        player.loadTexture("assets/ch16/sphere-pos-neg.png")
        mainStage3D.setCameraPosition(3f, 4f, 10f)
        mainStage3D.setCameraDirection(0f, 0f, 0f)
    }

    override fun update(dt: Float) {
        val speed = 3.0f
        val rotateSpeed = 45.0f
        if (!(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT))) {
            if (Gdx.input.isKeyPressed(Input.Keys.W)) player.moveForward(speed * dt)
            if (Gdx.input.isKeyPressed(Input.Keys.S)) player.moveForward(-speed * dt)
            if (Gdx.input.isKeyPressed(Input.Keys.A)) player.moveRight(-speed * dt)
            if (Gdx.input.isKeyPressed(Input.Keys.D)) player.moveRight(speed * dt)
            if (Gdx.input.isKeyPressed(Input.Keys.Q)) player.turnBy(-rotateSpeed * dt)
            if (Gdx.input.isKeyPressed(Input.Keys.E)) player.turnBy(rotateSpeed * dt)
            if (Gdx.input.isKeyPressed(Input.Keys.R)) player.moveUp(speed * dt)
            if (Gdx.input.isKeyPressed(Input.Keys.F)) player.moveUp(-speed * dt)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
            if (Gdx.input.isKeyPressed(Input.Keys.W)) mainStage3D.moveCameraForward(speed * dt)
            if (Gdx.input.isKeyPressed(Input.Keys.S)) mainStage3D.moveCameraForward(-speed * dt)
            if (Gdx.input.isKeyPressed(Input.Keys.A)) mainStage3D.moveCameraRight(speed * dt)
            if (Gdx.input.isKeyPressed(Input.Keys.D)) mainStage3D.moveCameraRight(-speed * dt)
            if (Gdx.input.isKeyPressed(Input.Keys.R)) mainStage3D.moveCameraUp(speed * dt)
            if (Gdx.input.isKeyPressed(Input.Keys.F)) mainStage3D.moveCameraUp(-speed * dt)
            if (Gdx.input.isKeyPressed(Input.Keys.Q)) mainStage3D.turnCamera(-rotateSpeed * dt)
            if (Gdx.input.isKeyPressed(Input.Keys.E)) mainStage3D.turnCamera(rotateSpeed * dt)
            if (Gdx.input.isKeyPressed(Input.Keys.T)) mainStage3D.tiltCamera(-rotateSpeed * dt)
            if (Gdx.input.isKeyPressed(Input.Keys.G)) mainStage3D.tiltCamera(rotateSpeed * dt)
        }
    }
}