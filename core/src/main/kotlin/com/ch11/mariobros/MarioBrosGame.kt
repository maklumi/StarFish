package com.ch11.mariobros

import com.BaseGame

class MarioBrosGame : BaseGame() {
    override fun create() {
        super.create()
        setActiveScreen(LevelScreen())
    }
}