package com.ch09.solitaire

import com.badlogic.gdx.scenes.scene2d.Stage
import com.ch09.jigsaw.DragAndDropActor

class Card(x: Float, y: Float, s: Stage) : DragAndDropActor(x, y, s) {

    var rankValue: Int = 0
    var suitValue: Int = 0

    fun setRankSuitValues(r: Int, s: Int) {
        rankValue = r
        suitValue = s
        val rankName = rankNames[r]
        val suitName = suitNames[s]
        val imageFileName = ("assets/ch09/solitaire/card$suitName$rankName.png")
        loadTexture(imageFileName)
        setSize(80f, 100f)
        setBoundaryRectangle()
    }

    override fun onDrop() {
        if (hasDropTarget) {
            val pile = dropTarget as Pile
            val topCard = pile.topCard

            if (rankValue == topCard.rankValue + 1 && suitValue == topCard.suitValue) {
                moveToActor(pile)
                pile.addCard(this)
                draggable = false
            } else {
                // avoid blocking view of pile when incorrect.
                moveToStart()
            }
        }
    }

    override fun act(dt: Float) {
        super.act(dt)
        boundToWorld()
    }

    companion object {
        val rankNames = arrayOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
        val suitNames = arrayOf("Clubs", "Hearts", "Spades", "Diamonds")
    }
}