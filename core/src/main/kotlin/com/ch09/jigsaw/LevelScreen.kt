package com.ch09.jigsaw

import com.BaseActor
import com.BaseGame
import com.BaseScreen
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Label


class LevelScreen : BaseScreen() {

    private lateinit var messageLabel: Label

    override fun initialize() {
        val background = BaseActor(0f, 0f, mainStage)
        background.loadTexture("assets/ch09/jigsaw/background.jpg")

        val numberRows = 3
        val numberCols = 3

        val texture = Texture(Gdx.files.internal("assets/ch09/jigsaw/sun.jpg"), true)
        val imageWidth = texture.width
        val imageHeight = texture.height
        val pieceWidth = imageWidth / numberCols
        val pieceHeight = imageHeight / numberRows

        val temp = TextureRegion.split(texture, pieceWidth, pieceHeight)

        for (r in 0 until numberRows) {
            for (c in 0 until numberCols) {
                // create puzzle piece at random location on left half of screen
                val pieceX = MathUtils.random(0, 400 - pieceWidth)
                val pieceY = MathUtils.random(0, 600 - pieceHeight)
                val pp = PuzzlePiece(pieceX.toFloat(), pieceY.toFloat(), mainStage)

                val anim = Animation<TextureRegion>(1f, temp[r][c])
                pp.setAnimation(anim)
                pp.row = r
                pp.col = c

                // setup grid of puzzle area objects on the right side of screen
                val borderX = (400 - imageWidth) / 2
                val borderY = (600 - imageHeight) / 2

                val areaX = 400 + borderX + pieceWidth * c
                val areaY = 600 - borderY - pieceHeight - pieceHeight * r

                val pa = PuzzleArea(areaX.toFloat(), areaY.toFloat(), mainStage)
                pa.setSize(pieceWidth.toFloat(), pieceHeight.toFloat())
                pa.setBoundaryRectangle()
                pa.row = r
                pa.col = c
            }
        }

        messageLabel = Label("...", BaseGame.labelStyle)
        messageLabel.color = Color.CYAN
        uiTable.add<Actor>(messageLabel).expandX().expandY().bottom().pad(50f)
        messageLabel.isVisible = false
    }

    override fun update(dt: Float) {
        // check to see if pieces are in correct positions
        var solved = true
        for (actor in BaseActor.getList(mainStage, PuzzlePiece::class)) {
            val pp = actor as PuzzlePiece

            if (!pp.isCorrectlyPlaced())
                solved = false
        }

        if (solved) {
            messageLabel.setText("You win!")
            messageLabel.isVisible = true
        } else {
            messageLabel.setText("...")
            messageLabel.isVisible = false
        }
    }
}