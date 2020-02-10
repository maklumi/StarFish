package com.ch10.brickbreaker

import com.BaseGame

class BreakoutGame : BaseGame() {

    override fun create() {
        super.create()
        setActiveScreen(LevelScreen())
    }

}