package com.ch05.visualnovel

class HomeworkGame : BaseGame() {

    override fun create() {
        super.create()
        setActiveScreen(MenuScreen())
    }

}