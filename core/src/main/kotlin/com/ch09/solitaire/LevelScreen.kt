package com.ch09.solitaire

import com.BaseActor
import com.BaseGame
import com.BaseScreen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Label
import java.util.*


class LevelScreen : BaseScreen() {

    private lateinit var pileList: ArrayList<Pile>
    private lateinit var messageLabel: Label

    override fun initialize() {
        val background = BaseActor(0f, 0f, mainStage)
        background.loadTexture("assets/ch09/solitaire/felt.jpg")
        BaseActor.setWorldBounds(background)

        for (r in Card.rankNames.indices) {
            for (s in Card.suitNames.indices) {
                val x = MathUtils.random(0f, 800f)
                val y = MathUtils.random(0f, 300f)
                val c = Card(x, y, mainStage)
                c.setRankSuitValues(r, s)

                // high-rank cards should appear below low-rank cards
                c.toBack()
            }
        }

        background.toBack() // else cards will render without background

        // create row of piles
        pileList = ArrayList()
        for (i in 0..3) {
            val pileX = 120 + 150f * i
            val pileY = 450f
            val pile = Pile(pileX, pileY, mainStage)
            pileList.add(pile)
        }

        // put an ace on the piles first
        for (actor in BaseActor.getList(mainStage, Card::class)) {
            val card = actor as Card
            if (card.rankValue == 0) {
                val pile = pileList[card.suitValue]
                card.toFront()
                card.moveToActor(pile)
                pile.addCard(card)
                card.draggable = false
            }
        }

        messageLabel = Label("...", BaseGame.labelStyle)
        messageLabel.color = Color.CYAN
        uiTable.add<Actor>(messageLabel).expandX().expandY().bottom().pad(50f)
        messageLabel.isVisible = false
    }

    override fun update(dt: Float) {
        // check to see if every pile contains all cards (13)
        var complete = true
        for (pile in pileList) {
            if (pile.size < 13)
                complete = false
        }

        if (complete) {
            messageLabel.setText("You win!")
            messageLabel.isVisible = true
        }
    }
}