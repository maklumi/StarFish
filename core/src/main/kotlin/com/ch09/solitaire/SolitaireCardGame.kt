package com.ch09.solitaire

import com.BaseGame

class SolitaireCardGame : BaseGame() {

    override fun create() {
        super.create()
        setActiveScreen(LevelScreen())
    }

}