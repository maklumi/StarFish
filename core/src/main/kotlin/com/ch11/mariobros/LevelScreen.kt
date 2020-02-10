package com.ch11.mariobros

import com.BaseActor
import com.BaseGame
import com.BaseScreen
import com.TilemapActor
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import java.util.*
import kotlin.math.abs


class LevelScreen : BaseScreen() {

    private lateinit var jack: Koala

    private var gameOver: Boolean = false
    private var coins: Int = 0
    private var time: Float = 30f

    private lateinit var coinLabel: Label
    private lateinit var timeLabel: Label
    private lateinit var messageLabel: Label
    private var keyTable: Table = Table()

    private val keyList: ArrayList<Color> = ArrayList()

    override fun initialize() {
        val tma = TilemapActor("assets/ch11/mario/map2.tmx", mainStage)

        for (obj in tma.getRectangleList("solid")) {
            val props = obj.properties
            Solid(props.get("x") as Float, props.get("y") as Float,
                  props.get("width") as Float, props.get("height") as Float,
                  mainStage)
        }

        val startPoint = tma.getRectangleList("Start")[0]
        val startProps = startPoint.properties
        jack = Koala(startProps.get("x") as Float, startProps.get("y") as Float, mainStage)

        for (obj in tma.getTileList("Flag")) {
            val props = obj.properties
            Flag(props.get("x") as Float, props.get("y") as Float, mainStage)
        }

        for (obj in tma.getTileList("Coin")) {
            val props = obj.properties
            Coin(props.get("x") as Float, props.get("y") as Float, mainStage)
        }

        tma.getTileList("Timer").forEach { obj ->
            val props = obj.properties
            Timer(props.get("x") as Float, props.get("y") as Float, mainStage)
        }

        tma.getTileList("Springboard").forEach { obj ->
            val props = obj.properties
            Springboard(props.get("x") as Float, props.get("y") as Float, mainStage)
        }

        for (obj in tma.getTileList("Platform")) {
            val props = obj.properties
            Platform(props.get("x") as Float, props.get("y") as Float, mainStage)
        }

        for (obj in tma.getTileList("Key")) {
            val props = obj.properties
            val key = Key(props.get("x") as Float, props.get("y") as Float, mainStage)
            val color = props.get("color") as String
            if (color == "red")
                key.color = Color.RED
            else
            // default color
                key.color = Color.WHITE
        }

        for (obj in tma.getTileList("Lock")) {
            val props = obj.properties
            val lock = Lock(props.get("x") as Float, props.get("y") as Float, mainStage)
            val color = props.get("color") as String
            if (color == "red")
                lock.color = Color.RED
            else
            // default
                lock.color = Color.WHITE
        }

        coinLabel = Label("Coins: $coins", BaseGame.labelStyle).also { it.color = Color.GOLD }
        timeLabel = Label("Time: " + time.toInt(), BaseGame.labelStyle).also { it.color = Color.LIGHT_GRAY }
        messageLabel = Label("Message", BaseGame.labelStyle).also { it.isVisible = false }

        uiTable.pad(20f)
        uiTable.add(coinLabel)
        uiTable.add(keyTable).expandX()
        uiTable.add(timeLabel)
        uiTable.row()
        uiTable.add(messageLabel).colspan(3).expandY()
    }

    override fun update(dt: Float) {
        if (gameOver)
            return

        for (flag in BaseActor.getList(mainStage, Flag::class)) {
            if (jack.overlaps(flag)) {
                messageLabel.setText("You Win!")
                messageLabel.color = Color.LIME
                messageLabel.isVisible = true
                jack.remove()
                gameOver = true
            }
        }

        BaseActor.getList(mainStage, Coin::class).forEach { coin ->
            if (jack.overlaps(coin)) {
                coins++
                coinLabel.setText("Coins: $coins")
                coin.remove()
            }
        }

        time -= dt
        timeLabel.setText("Time: " + time.toInt())

        for (timer in BaseActor.getList(mainStage, Timer::class)) {
            if (jack.overlaps(timer)) {
                time += 20
                timer.remove()
            }
        }

        if (time <= 0) {
            messageLabel.setText("Time Up - Game Over")
            messageLabel.color = Color.RED
            messageLabel.isVisible = true
            jack.remove()
            gameOver = true
        }

        for (springboard in BaseActor.getList(mainStage, Springboard::class)) {
            if (jack.belowOverlaps(springboard) && jack.isFalling()) {
                jack.spring()
            }
        }

        for (actor in BaseActor.getList(mainStage, Solid::class)) {
            val solid = actor as Solid

            if (solid is Platform) {
                // disable the solid when jumping up through
                if (jack.isJumping() && jack.overlaps(solid))
                    solid.isEnabled = false

                // when jumping, after passing through, re-enable the solid
                if (jack.isJumping() && !jack.overlaps(solid))
                    solid.isEnabled = true

                // disable the solid when jumping down through: code is in keyDown method

                // when falling, after passing through, re-enable the solid
                if (jack.isFalling() && !jack.overlaps(solid) && !jack.belowOverlaps(solid))
                    solid.isEnabled = true
            }

            if (solid is Lock && jack.overlaps(solid)) {
                val lockColor = solid.getColor()
                if (keyList.contains(lockColor)) {
                    solid.isEnabled = false
                    solid.addAction(Actions.fadeOut(0.5f))
                    solid.addAction(Actions.after(Actions.removeActor()))
                }
            }

            if (jack.overlaps(solid) && solid.isEnabled) {
                val offset = jack.preventOverlap(solid)

                if (offset != null) {
                    // collided in X direction
                    if (abs(offset.x) > abs(offset.y))
                        jack.velocityVec.x = 0f
                    else
                    // collided in Y direction
                        jack.velocityVec.y = 0f
                }

            }
        }

        for (key in BaseActor.getList(mainStage, Key::class)) {
            if (jack.overlaps(key)) {
                val keyColor = key.color
                key.remove()

                val keyIcon = BaseActor(0f, 0f, uiStage)
                keyIcon.loadTexture("assets/ch11/mario/key-icon.png")
                keyIcon.color = keyColor
                keyTable.add(keyIcon)

                keyList.add(keyColor)
            }
        }
    }

    override fun keyDown(keycode: Int): Boolean {
        if (gameOver)
            return false

        if (!mainStage.actors.contains(jack)) return false

        if (keycode == Keys.SPACE) {
            // if down arrow is held while jump is pressed and koala is above a platform,
            //   then the koala can fall down through it.
            if (Gdx.input.isKeyPressed(Keys.DOWN)) {
                for (actor in BaseActor.getList(mainStage, Platform::class)) {
                    val platform = actor as Platform
                    if (jack.belowOverlaps(platform)) {
                        platform.isEnabled = false
                    }
                }
            } else if (jack.isOnSolid()) {
                jack.jump()
            }
        }
        return false
    }
}