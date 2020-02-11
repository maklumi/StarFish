package com.ch14

import com.BaseGame

class MazeGame : BaseGame() {
    override fun create() {
        super.create()
        setActiveScreen(LevelScreen())
    }
}