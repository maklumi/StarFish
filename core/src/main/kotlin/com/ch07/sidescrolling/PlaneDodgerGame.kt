package com.ch07.sidescrolling

import com.BaseGame


class PlaneDodgerGame : BaseGame() {

    override fun create() {
        super.create()
        setActiveScreen(LevelScreen())
    }

}