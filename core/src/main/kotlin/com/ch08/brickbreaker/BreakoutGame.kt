package com.ch08.brickbreaker

import com.BaseGame

class BreakoutGame : BaseGame() {

    override fun create() {
        super.create()
        setActiveScreen(LevelScreen())
    }

}