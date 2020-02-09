package com.ch06.starfish.cutscene

import com.badlogic.gdx.scenes.scene2d.Actor
import java.util.*

class Scene : Actor() {
    private val segmentList = ArrayList<SceneSegment>()
    private var index: Int = -1

    private val isSegmentFinished: Boolean
        get() = segmentList[index].isFinished

    private val isLastSegment: Boolean
        get() = index >= segmentList.size - 1

    val isSceneFinished: Boolean
        get() = isLastSegment && isSegmentFinished

    fun addSegment(segment: SceneSegment) = segmentList.add(segment)

    fun addSegments(vararg segments: SceneSegment) = segments.forEach { segmentList.add(it) }

    fun clearSegments() = segmentList.clear()

    fun start() {
        index = 0
        segmentList[index].start()
    }

    override fun act(dt: Float) {
        if (isSegmentFinished && !isLastSegment)
            loadNextSegment()
    }

    fun loadNextSegment() {
        if (isLastSegment)
            return

        segmentList[index].finish()
        index++
        segmentList[index].start()
    }
}