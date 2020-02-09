package com.ch06.starfish


class StarfishGame : BaseGame() {

    override fun create() {
        super.create()
        setActiveScreen(MenuScreen())
    }

}