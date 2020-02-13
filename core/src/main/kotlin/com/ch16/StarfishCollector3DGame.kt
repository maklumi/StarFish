package com.ch16

class StarfishCollector3DGame : BaseGame() {
    override fun create() {
        super.create()
        setActiveScreen(LevelScreen())
    }
}