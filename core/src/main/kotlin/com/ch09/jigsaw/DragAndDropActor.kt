package com.ch09.jigsaw

import com.BaseActor
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions


open class DragAndDropActor(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {

    private var grabOffsetX: Float = 0f
    private var grabOffsetY: Float = 0f

    private var startPositionX: Float = 0f
    private var startPositionY: Float = 0f

    /**
     * whether this actor can be dragged.
     */
    var draggable: Boolean = true

    /**
     * set when actor is dropped on a target.
     */
    var dropTarget: DropTargetActor? = null

    init {
        this.addListener(
                object : InputListener() {
                    override fun touchDown(event: InputEvent?, eventOffsetX: Float, eventOffsetY: Float, pointer: Int, button: Int): Boolean {
                        if (!draggable)
                            return false

                        grabOffsetX = eventOffsetX
                        grabOffsetY = eventOffsetY

                        // store original position
                        // in case actor needs to return to it later
                        startPositionX = x
                        startPositionY = y

                        toFront()

                        // increase size; object appears larger when lifted by player
                        addAction(Actions.scaleTo(1.1f, 1.1f, 0.25f))

                        onDragStart()

                        return true // returning true indicates other touch methods are called
                    }

                    override fun touchDragged(event: InputEvent?, eventOffsetX: Float, eventOffsetY: Float, pointer: Int) {
                        val deltaX = eventOffsetX - grabOffsetX
                        val deltaY = eventOffsetY - grabOffsetY

                        moveBy(deltaX, deltaY)
                    }

                    override fun touchUp(e: InputEvent?, eventOffsetX: Float, eventOffsetY: Float, pointer: Int, button: Int) {
                        dropTarget = null

                        // keep track of distance to closest object
                        var closestDistance = Float.MAX_VALUE

                        for (actor in BaseActor.getList(stage, DropTargetActor::class)) {
                            val target = actor as DropTargetActor

                            if (target.isTarget && overlaps(target)) {
                                val currentDistance = Vector2.dst(x, y, target.x, target.y)

                                // check if this target is even closer
                                if (currentDistance < closestDistance) {
                                    dropTarget = target
                                    closestDistance = currentDistance
                                }
                            }
                        }

                        // return object to original size when dropped by player
                        addAction(Actions.scaleTo(1.00f, 1.00f, 0.25f))

                        onDrop()
                    }
                }
        )
    }

    /** Check if a drop target currently exists. */
    val hasDropTarget: Boolean
        get() = dropTarget != null

    /** Called when drag begins; extending classes may override this method. */
    open fun onDragStart() {}

    /** Called when drop occurs; extending classes may override this method. */
    open fun onDrop() {}

    /** Slide this actor to the center of another actor. */
    fun <T : BaseActor> moveToActor(other: T) {
        val x = other.x + (other.width - this.width) / 2
        val y = other.y + (other.height - this.height) / 2
        addAction(Actions.moveTo(x, y, 0.50f, Interpolation.pow3))
    }

    /** Slide this actor back to its original position before it was dragged. */
    fun moveToStart() {
        addAction(Actions.moveTo(startPositionX, startPositionY, 0.50f, Interpolation.pow3))
    }

}