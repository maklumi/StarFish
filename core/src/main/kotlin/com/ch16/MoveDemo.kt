package com.ch16

class MoveDemo : BaseGame() {
    override fun create() {
        super.create()
        setActiveScreen(DemoScreen())
    }
}