package com.ch09.jigsaw

import com.badlogic.gdx.scenes.scene2d.Stage

class PuzzlePiece(x: Float, y: Float, s: Stage) : DragAndDropActor(x, y, s) {

    var row: Int = 0
    var col: Int = 0

    private var puzzleArea: PuzzleArea? = null

    fun isCorrectlyPlaced(): Boolean {
        return if (puzzleArea == null) false
        else (puzzleArea!!.row == this.row && puzzleArea!!.col == this.col)
    }

    private fun clearPuzzleArea() {
        puzzleArea = null
    }

    private val hasPuzzleArea: Boolean
        get() = puzzleArea != null


    // override method from DragAndDropActor class
    override fun onDragStart() {
        if (hasPuzzleArea) {
            val pa = puzzleArea as PuzzleArea
            pa.isTarget = true
            clearPuzzleArea()
        }
    }

    // override method from DragAndDropActor class
    override fun onDrop() {
        if (hasDropTarget) {
            val pa = dropTarget as PuzzleArea
            moveToActor(pa)
            puzzleArea = pa
            pa.isTarget = false
        }
    }
}