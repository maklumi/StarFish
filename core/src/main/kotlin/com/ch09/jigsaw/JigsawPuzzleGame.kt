package com.ch09.jigsaw

import com.BaseGame


class JigsawPuzzleGame : BaseGame() {

    override fun create() {
        super.create()
        setActiveScreen(LevelScreen())
    }

}