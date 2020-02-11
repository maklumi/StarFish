package com.ch14

import com.BaseActor
import com.BaseGame
import com.BaseScreen
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label

class LevelScreen : BaseScreen() {
    private lateinit var maze: Maze
    private lateinit var hero: Hero
    private lateinit var ghost: Ghost
    private lateinit var coinsLabel: Label
    private lateinit var messageLabel: Label
    private lateinit var coinSound: Sound
    private lateinit var windMusic: Music

    override fun initialize() {
        val background = BaseActor(0f, 0f, mainStage)
        background.loadTexture("assets/ch14/white.png")
        background.color = Color.GRAY
        background.setSize(768f, 700f)
        maze = Maze(mainStage)
        hero = Hero(0f, 0f, mainStage)
        hero.centerAtActor(maze.getRoom(0, 0)!!)
        ghost = Ghost(0f, 0f, mainStage)
        ghost.centerAtActor(maze.getRoom(11, 9)!!)
        for (room in BaseActor.getList(mainStage, Room::class)) {
            val coin = Coin(0f, 0f, mainStage)
            coin.centerAtActor(room)
        }
        ghost.toFront()
        coinsLabel = Label("Coins left:", BaseGame.labelStyle)
        coinsLabel.color = Color.GOLD
        messageLabel = Label("...", BaseGame.labelStyle)
        messageLabel.setFontScale(2f)
        messageLabel.isVisible = false
        uiTable.pad(10f)
        uiTable.add(coinsLabel)
        uiTable.row()
        uiTable.add(messageLabel).expandY()
        coinSound = Gdx.audio.newSound(Gdx.files.internal("assets/ch14/coin.wav"))
        windMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/ch14/wind.mp3"))
        windMusic.isLooping = true
        windMusic.volume = 0.1f
        windMusic.play()
    }

    override fun update(dt: Float) {
        for (wall in BaseActor.getList(mainStage, Wall::class)) {
            hero.preventOverlap(wall)
        }
        if (ghost.actions.size == 0) {
            maze.resetRooms()
            ghost.findPath(maze.getRoom(ghost)!!, maze.getRoom(hero)!!)
        }
        for (coin in BaseActor.getList(mainStage, Coin::class)) {
            if (hero.overlaps(coin)) {
                coin.remove()
                coinSound.play(0f)
            }
        }
        val coins: Int = BaseActor.count(mainStage, Coin::class)
        coinsLabel.setText("Coins left: $coins")
        if (coins == 0) {
            ghost.remove()
            ghost.setPosition(-1000f, -1000f)
            ghost.clearActions()
            ghost.addAction(Actions.forever(Actions.delay(1f)))
            messageLabel.setText("You win!")
            messageLabel.color = Color.GREEN
            messageLabel.isVisible = true
        }
        if (hero.overlaps(ghost)) {
            hero.remove()
            hero.setPosition(-1000f, -1000f)
            ghost.clearActions()
            ghost.addAction(Actions.forever(Actions.delay(1f)))
            messageLabel.setText("Game Over")
            messageLabel.color = Color.RED
            messageLabel.isVisible = true
        }
        if (!messageLabel.isVisible) {
            val distance = Vector2(hero.x - ghost.x, hero.y - ghost.y).len()
            var volume = -(distance - 64) / (300 - 64) + 1
            volume = MathUtils.clamp(volume, 0.10f, 1.00f)
            windMusic.volume = volume
        }
    }

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Input.Keys.R) BaseGame.setActiveScreen(LevelScreen())
        if (keycode == Input.Keys.Q) Gdx.app.exit()
        return false
    }
}