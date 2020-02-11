package com.ch14

import com.BaseActor
import com.badlogic.gdx.scenes.scene2d.Stage
import kotlin.math.floor

class Room(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {

    private val wallArray = Array(4) {
        Wall(-100f, -100f, 0f, 0f, s) // will be reassigned
    }


    private val neighborArray: Array<Room?> = Array(4) {
        // some may be null!
        null
    }

    init {
        loadTexture("assets/ch14/dirt.png")
        val w = 64f
        val h = 64f
        val t = 6f
        setSize(w, h)
        // t = wall thickness in pixels

        wallArray[SOUTH] = Wall(x, y, w, t, s)
        wallArray[WEST] = Wall(x, y, t, h, s)
        wallArray[NORTH] = Wall(x, y + h - t, w, t, s)
        wallArray[EAST] = Wall(x + w - t, y, t, h, s)
    }

    var isConnected: Boolean = false    // used in maze generation
    var isVisited: Boolean = false      // used in pathfinding
    var previousRoom: Room? = null      // used in pathfinding

    fun setNeighbor(direction: Int, neighbor: Room?) {
        neighborArray[direction] = neighbor
    }

    fun hasNeighbor(direction: Int): Boolean {
        return neighborArray[direction] != null
    }

    private fun getNeighbor(direction: Int): Room? {
        return neighborArray[direction]
    }

    // check if wall in this direction still exists
    fun hasWall(direction: Int): Boolean {
        return wallArray[direction].stage != null
    }

    fun removeWalls(direction: Int) {
        removeWallsBetween(neighborArray[direction])
    }

    fun removeWallsBetween(other: Room?) {
        if (other == null) return
        when (other) {
            neighborArray[NORTH] -> {
                wallArray[NORTH].remove()
                other.wallArray[SOUTH].remove()
            }
            neighborArray[SOUTH] -> {
                wallArray[SOUTH].remove()
                other.wallArray[NORTH].remove()
            }
            neighborArray[EAST] -> {
                wallArray[EAST].remove()
                other.wallArray[WEST].remove()
            }
            neighborArray[WEST] -> {
                wallArray[WEST].remove()
                other.wallArray[EAST].remove()
            }
        }
    }

    fun hasUnconnectedNeighbor(): Boolean {
        for (direction in directionArray) {
            if (hasNeighbor(direction) &&
                    getNeighbor(direction) != null &&
                    !getNeighbor(direction)!!.isConnected) return true
        }
        return false
    }

    val randomUnconnectedNeighbor: Room
        get() {
            val directionList = ArrayList<Int>()
            for (direction in directionArray) {
                if (hasNeighbor(direction) &&
                        getNeighbor(direction) != null &&
                        !getNeighbor(direction)!!.isConnected) directionList.add(direction)
            }
            val directionIndex = floor(Math.random() * directionList.size).toInt()
            val direction = directionList[directionIndex]
            return getNeighbor(direction)!!
        }

    // Used in pathfinding: locate accessible neighbors that have not yet been visited
    fun unvisitedPathList(): ArrayList<Room> {
        val list = ArrayList<Room>()
        for (direction in directionArray) {
            if (hasNeighbor(direction) &&
                    !hasWall(direction) &&
                    getNeighbor(direction) != null &&
                    !getNeighbor(direction)!!.isVisited) list.add(getNeighbor(direction)!!)
        }
        return list
    }

    companion object {
        const val NORTH = 0
        const val SOUTH = 1
        const val EAST = 2
        const val WEST = 3
        val directionArray = intArrayOf(NORTH, SOUTH, EAST, WEST)
    }


}