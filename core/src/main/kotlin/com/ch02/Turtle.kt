package com.ch02

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys

class Turtle : ActorBeta() {

    override fun act(dt: Float) {
        super.act(dt)

        if (Gdx.input.isKeyPressed(Keys.LEFT))
            this.moveBy(-1f, 0f)
        if (Gdx.input.isKeyPressed(Keys.RIGHT))
            this.moveBy(1f, 0f)
        if (Gdx.input.isKeyPressed(Keys.UP))
            this.moveBy(0f, 1f)
        if (Gdx.input.isKeyPressed(Keys.DOWN))
            this.moveBy(0f, -1f)
    }
}