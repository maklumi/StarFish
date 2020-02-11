package com.ch13.gamepad

import com.ch06.starfish.BaseGame
import com.ch06.starfish.MenuScreen


class StarfishGame : BaseGame() {

    override fun create() {
        super.create()
        setActiveScreen(MenuScreen())
    }

}