package com.ch06.rhythm

class RecorderGame : BaseGame() {
    override fun create() {
        super.create()
        setActiveScreen(RecorderScreen())
    }

    override fun setGameToThis() {
        game = this
    }
}