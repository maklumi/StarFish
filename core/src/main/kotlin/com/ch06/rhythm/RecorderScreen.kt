package com.ch06.rhythm

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.ui.TextButton


class RecorderScreen : BaseScreen() {
    private var music: Music? = null
    private lateinit var songData: SongData
    private var lastSongPosition: Float = 0f
    private var recording: Boolean = false
    private lateinit var loadButton: TextButton
    private lateinit var recordButton: TextButton
    private lateinit var saveButton: TextButton

    override fun initialize() {
        loadButton = TextButton("Load Music File", BaseGame.textButtonStyle)
        loadButton.addListener loadButton@{ e: Event ->
            if (!isTouchDownEvent(e))
                return@loadButton false

//            val musicFile = FileUtils.showOpenDialog()
            val musicFile = FileHandle(Gdx.files.internal("assets/ch06/rhythm/FunkyJunky.mp3").file())

            if (musicFile != null) {
                music = Gdx.audio.newMusic(musicFile)
                songData = SongData()
                songData.songName = musicFile.name()
            }

            true
        }

        recordButton = TextButton("Record Keystrokes", BaseGame.textButtonStyle)

        recordButton.addListener recordButton@{ e: Event ->
            if (!isTouchDownEvent(e))
                return@recordButton false

            if (!recording) {
                music?.play()
                recording = true
                lastSongPosition = 0f
            }

            true
        }

        saveButton = TextButton("Save Keystroke File", BaseGame.textButtonStyle)
        saveButton.addListener saveButton@{ e: Event ->
            if (!isTouchDownEvent(e))
                return@saveButton false

//            val textFile = FileUtils.showSaveDialog()
            val textFile = FileHandle(Gdx.files.internal("assets/ch06/rhythm/my_rhythm.txt").file())

            if (textFile != null)
                songData.writeToFile(textFile)

            true
        }

        uiTable.add(loadButton)
        uiTable.row()
        uiTable.add(recordButton)
        uiTable.row()
        uiTable.add(saveButton)
    }

    override fun update(dt: Float) {
        recordButton.isVisible = music != null
        if (recording) {
            if (music!!.isPlaying)
                lastSongPosition = music!!.position
            else
            // song just finished
            {
                recording = false
                songData.songDuration = lastSongPosition
            }
        }
    }

    override fun keyDown(keycode: Int): Boolean {
        if (recording) {
            val key = Keys.toString(keycode)
            val time = music!!.position
            songData.addKeyTime(key, time)
        }
        return false
    }
}