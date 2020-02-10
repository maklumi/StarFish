package com.ch09.solitaire

import com.badlogic.gdx.scenes.scene2d.Stage
import com.ch09.jigsaw.DropTargetActor

class Pile(x: Float, y: Float, s: Stage) : DropTargetActor(x, y, s) {

    init {
        loadTexture("assets/ch09/solitaire/pile.png")
        setSize(100f, 120f)
        setBoundaryRectangle()
    }

    private val cardList = ArrayList<Card>()

    /** Get "top" card in the pile (remains in pile).  */
    val topCard: Card
        get() = cardList[0]

    /** Return the number of cards in this pile. */
    val size: Int
        get() = cardList.size

    /** Add a Card object to the "top" of the pile. */
    fun addCard(c: Card) {
        cardList.add(0, c)
    }
}