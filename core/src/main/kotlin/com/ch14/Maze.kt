package com.ch14

import com.BaseActor
import com.badlogic.gdx.scenes.scene2d.Stage
import kotlin.math.floor
import kotlin.math.roundToInt

class Maze(s: Stage) {
    // maze size constants
    private val roomCountX = 12
    private val roomCountY = 10
    private val roomWidth = 64
    private val roomHeight = 64
    private val roomGrid = Array<Array<Room?>>(roomCountX) {
        Array(roomCountY) { null }
    }

    fun getRoom(gridX: Int, gridY: Int): Room? {
        return roomGrid[gridX][gridY]
    }

    fun getRoom(actor: BaseActor): Room? {
        val gridX = (actor.x / roomWidth).roundToInt()
        val gridY = (actor.y / roomHeight).roundToInt()
        return getRoom(gridX, gridY)
    }

    fun resetRooms() {
        for (gridY in 0 until roomCountY) {
            for (gridX in 0 until roomCountX) {
                roomGrid[gridX][gridY]?.isVisited = false
                roomGrid[gridX][gridY]?.previousRoom = null
            }
        }
    }

    init {
        val startTime = System.currentTimeMillis()
        for (gridY in 0 until roomCountY) {
            for (gridX in 0 until roomCountX) {
                val pixelX = gridX * roomWidth.toFloat()
                val pixelY = gridY * roomHeight.toFloat()
                val room = Room(pixelX, pixelY, s)
                roomGrid[gridX][gridY] = room
            }
        }
        // neighbor relations
        for (gridY in 0 until roomCountY) {
            for (gridX in 0 until roomCountX) {
                val room = roomGrid[gridX][gridY]
                if (gridY > 0) room!!.setNeighbor(Room.SOUTH, roomGrid[gridX][gridY - 1])
                if (gridY < roomCountY - 1) room!!.setNeighbor(Room.NORTH, roomGrid[gridX][gridY + 1])
                if (gridX > 0) room!!.setNeighbor(Room.WEST, roomGrid[gridX - 1][gridY])
                if (gridX < roomCountX - 1) room!!.setNeighbor(Room.EAST, roomGrid[gridX + 1][gridY])
            }
        }
        val activeRoomList = ArrayList<Room?>()
        var currentRoom = roomGrid[0][0]
        currentRoom?.isConnected = true
        activeRoomList.add(0, currentRoom)
        // chance of returning to an already visited room
        //  to create a branching path from that room
        val branchProbability = 0.5f
        while (activeRoomList.size > 0) {
            currentRoom = if (Math.random() < branchProbability) { // get random previously visited room
                val roomIndex = (Math.random() * activeRoomList.size).toInt()
                activeRoomList[roomIndex]
            } else { // get the most recently visited room
                activeRoomList[activeRoomList.size - 1]
            }
            if (currentRoom!!.hasUnconnectedNeighbor()) {
                val nextRoom: Room = currentRoom.randomUnconnectedNeighbor
                currentRoom.removeWallsBetween(nextRoom)
                nextRoom.isConnected = true
                activeRoomList.add(0, nextRoom)
            } else {
                // this room has no more adjacent unconnected rooms
                // so there is no reason to keep it in the list
                activeRoomList.remove(currentRoom)
            }
        }
        // remove additional walls at random
        var wallsToRemove = 24
        while (wallsToRemove > 0) {
            val gridX = floor(Math.random() * roomCountX).toInt()
            val gridY = floor(Math.random() * roomCountY).toInt()
            val direction = floor(Math.random() * 4).toInt()
            val room = roomGrid[gridX][gridY]
            if (room!!.hasNeighbor(direction) && room.hasWall(direction)) {
                room.removeWalls(direction)
                wallsToRemove--
            }
        }
        val finishTime = System.currentTimeMillis()
        println("Time to generate maze: " + (finishTime - startTime))
        println("Press R to restart, Q to quit")
    }
}