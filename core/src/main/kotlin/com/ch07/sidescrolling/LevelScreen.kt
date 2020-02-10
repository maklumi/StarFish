package com.ch07.sidescrolling

import com.BaseActor
import com.BaseGame
import com.BaseScreen
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.ch07.sidescrolling.effects.Explosion
import com.ch07.sidescrolling.effects.Sparkle


class LevelScreen: BaseScreen() {

    private lateinit var plane: Plane

    private var starTimer: Float = 0f
    private var starSpawnInterval: Float = 4f

    private var score: Int = 0
    private lateinit var scoreLabel: Label

    // enemy
    private var enemyTimer: Float = 0f
    private var enemySpawnInterval: Float = 3f
    private var enemySpeed: Float = 100f

    private var gameOver: Boolean = false
    private lateinit var gameOverMessage: BaseActor

    // effects
    private lateinit var backgroundMusic: Music
    private lateinit var sparkleSound: Sound
    private lateinit var explosionSound: Sound

    override fun initialize() {
        Sky(0f, 0f, mainStage)
        Sky(800f, 0f, mainStage)

        Ground(0f, 0f, mainStage)
        Ground(800f, 0f, mainStage)

        plane = Plane(100f, 500f, mainStage)
        BaseActor.setWorldBounds(800f, 600f)

        // enemy
        gameOverMessage = BaseActor(0f, 0f, uiStage)
        gameOverMessage.loadTexture("assets/ch07/plane/game-over.png")
        gameOverMessage.isVisible = false

        scoreLabel = Label(score.toString(), BaseGame.labelStyle)
        uiTable.pad(10f)
        uiTable.add(scoreLabel)
        uiTable.row()
        uiTable.add(gameOverMessage).expandY()

        // effects
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/ch07/plane/Prelude-and-Action.mp3"))
        sparkleSound   = Gdx.audio.newSound(Gdx.files.internal("assets/ch07/plane/sparkle.mp3"))
        explosionSound   = Gdx.audio.newSound(Gdx.files.internal("assets/ch07/plane/explosion.wav"))

        backgroundMusic.isLooping = true
        backgroundMusic.volume = 0.2f
        backgroundMusic.play()
    }

    override fun update(dt: Float) {
        // prevent re-spawning if game over
        if (gameOver)
            return

        // spawn star regularly and reset timer
        starTimer += dt
        if (starTimer > starSpawnInterval) {
            Star(800f, MathUtils.random(100f, 500f), mainStage)
            starTimer = 0f
        }

        for (star in BaseActor.getList(mainStage, Star::class)) {
            if (plane.overlaps(star)) {
                val sp = Sparkle(0f, 0f, mainStage)
                sp.centerAtActor(star)
                sparkleSound.play()
                star.remove()
                score++
                scoreLabel.setText(score.toString())
            }
        }

        // enemy
        enemyTimer += dt
        if (enemyTimer > enemySpawnInterval) {
            val enemy = Enemy(800f, MathUtils.random(100f, 500f), mainStage)
            enemy.speed = enemySpeed

            enemyTimer = 0f
            enemySpawnInterval -= 0.10f
            enemySpeed += 10

            if (enemySpawnInterval < 0.5f)
                enemySpawnInterval = 0.5f

            if (enemySpeed > 400)
                enemySpeed = 400f
        }

        for (enemy in BaseActor.getList(mainStage, Enemy::class)) {
            if (plane.overlaps(enemy)) {
                val ex = Explosion(0f, 0f, mainStage)
                ex.centerAtActor(plane)
                ex.setScale(3f)
                explosionSound.play()
                backgroundMusic.stop()
                plane.remove()
                gameOver = true
                gameOverMessage.isVisible = true
            }

            if (enemy.x + enemy.width < 0) {
                score++
                scoreLabel.setText(score.toString())
                enemy.remove()
            }
        }

    }

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Input.Keys.SPACE)
            plane.boost()

        return true
    }

}