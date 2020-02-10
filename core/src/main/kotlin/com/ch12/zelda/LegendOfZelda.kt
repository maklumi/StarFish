package com.ch12.zelda

import com.BaseGame

class LegendOfZelda : BaseGame() {
    override fun create() {
        super.create()
        setActiveScreen(LevelScreen())
    }
}