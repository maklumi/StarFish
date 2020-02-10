package com.ch06.rhythm


class RhythmGame : BaseGame() {
    override fun create() {
        super.create()
        setActiveScreen(RhythmScreen())
    }

    override fun setGameToThis() {
        game = this
    }
}