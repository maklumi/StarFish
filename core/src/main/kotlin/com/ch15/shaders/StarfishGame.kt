package com.ch15.shaders

import com.ch06.starfish.BaseGame


class StarfishGame : BaseGame() {

    override fun create() {
        super.create()
        setActiveScreen(LevelScreen())
    }

}