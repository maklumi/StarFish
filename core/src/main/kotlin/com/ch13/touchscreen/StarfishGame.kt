package com.ch13.touchscreen

import com.ch06.starfish.BaseGame

class StarfishGame : BaseGame() {

    override fun create() {
        super.create()
        setActiveScreen(LevelScreen())
    }

}