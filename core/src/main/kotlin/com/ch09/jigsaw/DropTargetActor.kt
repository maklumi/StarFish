package com.ch09.jigsaw

import com.badlogic.gdx.scenes.scene2d.Stage
import com.BaseActor

open class DropTargetActor(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {

    /** whether this actor can be targeted by a DragAndDrop actor. */
    var isTarget: Boolean = true

}