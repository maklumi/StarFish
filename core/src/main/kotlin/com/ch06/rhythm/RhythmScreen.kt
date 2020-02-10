package com.ch06.rhythm

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.utils.Align
import java.util.*
import kotlin.math.abs


class RhythmScreen : BaseScreen() {
    private lateinit var keyList: ArrayList<String>
    private lateinit var colorList: ArrayList<Color>
    private lateinit var targetList: ArrayList<TargetBox>
    private lateinit var fallingLists: ArrayList<ArrayList<FallingBox>>

    private lateinit var gameMusic: Music
    private val songData = SongData()

    // how many seconds until NoteBox reaches TargetBox
    private val leadTime = 3f
    // advanceTimer is set to be leadTime seconds ahead of music time position
    private var advanceTimer = 0f
    private var spawnHeight = 650f
    private val noteSpeed: Float
        get() = (spawnHeight - targetList[0].y) / leadTime

    private lateinit var message: Message
    private lateinit var scoreLabel: Label
    private var score = 0
    private var maxScore = 0
    private lateinit var timeLabel: Label
    private var songDuration = 0f

    override fun initialize() {
        val background = BaseActor(0f, 0f, mainStage)
        background.loadTexture("ch06/rhythm/space.png")
        background.setSize(800f, 600f)
        BaseActor.setWorldBounds(background)

        keyList = ArrayList()
        val keyArray = arrayOf("F", "G", "H", "J")
//        Collections.addAll(keyList, *keyArray)
        keyList.addAll(keyArray)

        colorList = ArrayList()
        val colorArray = arrayOf(Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE)
        Collections.addAll(colorList, *colorArray)

        val targetTable = Table()
        targetTable.setFillParent(true)
        targetTable.add().colspan(4).expandY()
        targetTable.row()
        mainStage.addActor(targetTable)

        targetList = ArrayList()
        for (i in 0..3) {
            val tb = TargetBox(0f, 0f, mainStage, keyList[i], colorList[i])
            targetList.add(tb)
            targetTable.add(tb).pad(32f)
        }

        fallingLists = ArrayList()
        for (i in 0..3) {
            fallingLists.add(ArrayList())
        }

//        noteSpeed = (spawnHeight - targetList[0].y) / leadTime

        val startButton = TextButton("Start", BaseGame.textButtonStyle)
        startButton.addListener startButton@{ e: Event ->
            if (!isTouchDownEvent(e))
                return@startButton false

            val dataFileHandle = Gdx.files.internal("ch06/rhythm/FunkyJunky.key")
            songData.readFromFile(dataFileHandle)
            songData.resetIndex()

            val songFileHandle = Gdx.files.internal("ch06/rhythm/" + songData.songName.trim())
            gameMusic = Gdx.audio.newMusic(songFileHandle)
            gameMusic.volume = .2f
            startButton.isVisible = false

            songDuration = songData.songDuration
            maxScore = 100 * songData.keyTimeCount()
            scoreLabel.setText("Score: $score\nMax: $maxScore")
            timeLabel.setText("Time: 0\nEnd: $songDuration")

            message.displayCountdown()
            true
        }

        scoreLabel = Label("Score: 0" + "\n" + "Max: 0", BaseGame.labelStyle)
        scoreLabel.setAlignment(Align.right)

        timeLabel = Label("Time: 0" + "\n" + "End: 0", BaseGame.labelStyle)
        timeLabel.setAlignment(Align.right)

        message = Message(0f, 0f, uiStage)
        message.setOpacity(0f)

        uiTable.pad(10f)
        uiTable.add(startButton).width(200f).left()
        uiTable.add(timeLabel).width(150f)
        uiTable.add(scoreLabel).width(200f).right()
        uiTable.row()
        uiTable.add(message).colspan(3).expandX().expandY()
    }

    override fun update(dt: Float) {
        if (songData.songDuration <= 0)
            return

        if (advanceTimer < leadTime && advanceTimer + dt > leadTime)
            gameMusic.play()

        if (advanceTimer < leadTime)
            advanceTimer += dt
        else
            advanceTimer = leadTime + gameMusic.position

        while (!songData.isFinished && advanceTimer >= songData.currentKeyTime.time) {
            val key = songData.currentKeyTime.key
            val i = keyList.indexOf(key)

            val fb = FallingBox(targetList[i].x, spawnHeight, mainStage)
            fb.speed = noteSpeed
            fb.motionAngle = 270f
            fb.color = colorList[i]


            fallingLists[i].add(fb)

            songData.advanceIndex()

        }

        if (gameMusic.isPlaying)
            timeLabel.setText("Time: " + gameMusic.position.toInt() + "\n"
                    + "End: " + songDuration)

        // remove FallingBox instances that have passed below the target box
        for (i in 0..3) {
//            val key = keyList[i]
            val fallingList = fallingLists[i]
            if (fallingList.size > 0) {
                val fb = fallingList[0]
                val tb = targetList[i]
                if (fb.y < tb.y && !fb.overlaps(tb)) {
                    message.setAnimation(message.miss)
                    message.pulseFade()
                    fallingList.remove(fb)
                    fb.speed = 0f
                    fb.flashOut()
                }
            }
        }

        if (songData.isFinished && !gameMusic.isPlaying) {
            message.displayCongratulations()
            songData.songDuration = 0f
        }
    }

    override fun keyDown(keycode: Int): Boolean {
        if (songData.songDuration <= 0)
            return false

        val keyString = Keys.toString(keycode)
        if (keyList.contains(keyString)) {
            val i = keyList.indexOf(keyString)
            val tb = targetList[i]
            tb.pulse()
            val fallingList = fallingLists[i]

            if (fallingList.size == 0) {
                message.setAnimation(message.miss)
                message.pulseFade()
            } else {
                val fb = fallingList[0]
                val distance = abs(fb.y - tb.y)

                when {
                    distance < 8 -> {
                        message.setAnimation(message.perfect)
                        score += 100
                    }
                    distance < 16 -> {
                        message.setAnimation(message.great)
                        score += 80
                    }
                    distance < 24 -> {
                        message.setAnimation(message.good)
                        score += 50
                    }
                    distance < 32 -> {
                        message.setAnimation(message.almost)
                        score += 20
                    }
                    else -> message.setAnimation(message.miss)
                }

                message.pulseFade()
                scoreLabel.setText("Score: $score\nMax: $maxScore")

                fallingList.remove(fb)
                fb.speed = 0f
                fb.flashOut()
            }
        }

        return false
    }
}