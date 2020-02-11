package com.ch14

import com.BaseActor
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import java.util.*

class Ghost(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {

    override var speed = 30f // pixels per second

    fun findPath(startRoom: Room, targetRoom: Room) {
        var currentRoom: Room? = startRoom
        val roomList = ArrayList<Room>()
        currentRoom!!.previousRoom = null
        currentRoom.isVisited = true
        roomList.add(currentRoom)

        while (roomList.size > 0) {
            currentRoom = roomList.removeAt(0)
            for (nextRoom in currentRoom.unvisitedPathList()) {
                nextRoom.previousRoom = currentRoom
                if (nextRoom == targetRoom) { // target found!
                    roomList.clear()
                    break
                } else {
                    nextRoom.isVisited = true
                    roomList.add(nextRoom)
                }
            }
        }
        // create list of rooms corresponding to shortest path
        val pathRoomList = ArrayList<Room>()
        currentRoom = targetRoom
        while (currentRoom != null) {
            // add current room to beginning of list
            pathRoomList.add(0, currentRoom)
            currentRoom = currentRoom.previousRoom
        }
        // only move along a few steps of the path;
        // path will be recalculated these actions are complete.
        val maxStepCount = 2
        // to remove the pause between steps, start loop index at 1
        // but make ghost speed slower to compensate
        for (i in pathRoomList.indices) {
            if (i == maxStepCount) break
            val nextRoom: Room = pathRoomList[i]
            val move: Action = Actions.moveTo(nextRoom.x, nextRoom.y, 64 / speed)
            addAction(move)
        }
    }

    init {
        loadAnimationFromSheet("assets/ch14/ghost.png", 1, 3, 0.2f, true)
        setOpacity(0.8f)
    }
}