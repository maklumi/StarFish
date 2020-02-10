package com.ch06.rhythm

import com.badlogic.gdx.files.FileHandle
import java.util.*

class SongData {
    var songName: String = "key_time_pair.txt"
    var songDuration: Float = 0f
    private val keyTimeList = ArrayList<KeyTimePair>()
    private var keyTimeIndex: Int = 0

    val currentKeyTime: KeyTimePair
        get() = keyTimeList[keyTimeIndex]

    val isFinished: Boolean
        get() = keyTimeIndex >= keyTimeList.size - 2

    inner class KeyTimePair(val key: String, val time: Float)

    fun addKeyTime(k: String, t: Float) {
        keyTimeList.add(KeyTimePair(k, t))
    }

    fun resetIndex() {
        keyTimeIndex = 0
    }

    fun advanceIndex() {
        keyTimeIndex++
    }

    fun keyTimeCount(): Int {
        return keyTimeList.size
    }

    fun writeToFile(file: FileHandle) {
        // boolean: true=append, false=overwrite.
        file.writeString(songName + "\n", false)
        file.writeString(songDuration.toString() + "\n", true)
        for (ktp in keyTimeList) {
            val data = ktp.key + "," + ktp.time + "\n"
            file.writeString(data, true)
        }
    }

    fun readFromFile(file: FileHandle) {
        val rawData = file.readString()
        val dataArray = rawData.split("\n")
        songName = dataArray[0]
        songDuration = dataArray[1].toFloat()
        keyTimeList.clear()
        for (i in 2 until dataArray.size-1) {
            val keyTimeData = dataArray[i].split(",")
            val key = keyTimeData[0]
            val time = keyTimeData[1].toFloat()
            keyTimeList.add(KeyTimePair(key, time))
        }
    }
}