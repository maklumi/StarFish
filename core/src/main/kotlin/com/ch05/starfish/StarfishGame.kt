package com.ch05.starfish

class StarfishGame : BaseGame() {

    override fun create() {
        super.create()
        setActiveScreen(MenuScreen())
    }

}