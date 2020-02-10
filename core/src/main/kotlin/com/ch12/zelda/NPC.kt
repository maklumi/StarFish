package com.ch12.zelda

import com.BaseActor
import com.badlogic.gdx.scenes.scene2d.Stage

class NPC(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    // the text to be displayed
    var text: String = " "

    // used to determine if dialog box text is currently being displayed
    var isViewing: Boolean = false

    // ID used for specific graphics
    // and identifying NPCs with dynamic messages
    var id: String = "default"
        set(id) {
            field = id

            when (id) {
                "Gatekeeper" -> loadTexture("assets/ch12/zelda/npc-1.png")
                "Shopkeeper" -> loadTexture("assets/ch12/zelda/npc-2.png")
                "default" -> loadTexture("assets/ch12/zelda/npc-3.png")
            }
        }


}