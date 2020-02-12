package com.ch15.spacerock

import com.BaseGame

class SpaceGame : BaseGame() {

    override fun create() {
        super.create()
        setActiveScreen(LevelScreen())
    }

}