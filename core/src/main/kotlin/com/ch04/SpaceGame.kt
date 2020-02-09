package com.ch04

class SpaceGame : BaseGame() {

    override fun create() {
        super.create()
        setActiveScreen(LevelScreen())
    }

}