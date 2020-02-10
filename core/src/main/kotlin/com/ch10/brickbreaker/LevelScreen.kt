package com.ch10.brickbreaker

import com.BaseActor
import com.BaseGame
import com.BaseScreen
import com.TilemapActor
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.ch08.brickbreaker.*


class LevelScreen : BaseScreen() {

    private lateinit var paddle: Paddle
    private lateinit var ball: Ball

    private var score: Int = 0
    private var balls: Int = 3
    private lateinit var scoreLabel: Label
    private lateinit var ballsLabel: Label
    private lateinit var messageLabel: Label

    private lateinit var bounceSound: Sound
    private lateinit var brickBumpSound: Sound
    private lateinit var wallBumpSound: Sound
    private lateinit var itemAppearSound: Sound
    private lateinit var itemCollectSound: Sound
    private lateinit var backgroundMusic: Music
    private val volume = .1f

    override fun initialize() {
        val tma = TilemapActor("assets/ch08/breakout/map.tmx", mainStage)

        for (obj in tma.getTileList("Wall")) {
            val props = obj.properties
            Wall(props.get("x") as Float, props.get("y") as Float,
                 props.get("width") as Float, props.get("height") as Float,
                 mainStage)
        }

        for (obj in tma.getTileList("Brick")) {
            val props = obj.properties
            val b = Brick(props.get("x") as Float, props.get("y") as Float, mainStage)
            b.setSize(props.get("width") as Float, props.get("height") as Float)
            b.setBoundaryRectangle()

            when (props.get("color") as String) {
                "red" -> b.color = Color.RED
                "orange" -> b.color = Color.ORANGE
                "yellow" -> b.color = Color.YELLOW
                "green" -> b.color = Color.GREEN
                "blue" -> b.color = Color.BLUE
                "purple" -> b.color = Color.PURPLE
                "white" -> b.color = Color.WHITE
                "gray" -> b.color = Color.GRAY
            }
        }

        val startPoint = tma.getRectangleList("Start")[0]
        val props = startPoint.properties
        paddle = Paddle(props["x"] as Float, props["y"] as Float, mainStage)

        balls = 3
        ball = Ball(0f, 0f, mainStage)

        scoreLabel = Label("Score: $score", BaseGame.labelStyle)
        ballsLabel = Label("Balls: $balls", BaseGame.labelStyle)
        messageLabel = Label("click to start", BaseGame.labelStyle)
        messageLabel.color = Color.CYAN

        uiTable.pad(5f)
        uiTable.add(scoreLabel)
        uiTable.add().expandX()
        uiTable.add(ballsLabel)
        uiTable.row()
        uiTable.add(messageLabel).colspan(3).expandY()

        bounceSound = Gdx.audio.newSound(Gdx.files.internal("assets/ch08/breakout/boing.wav"))
        brickBumpSound = Gdx.audio.newSound(Gdx.files.internal("assets/ch08/breakout/bump.wav"))
        wallBumpSound = Gdx.audio.newSound(Gdx.files.internal("assets/ch08/breakout/bump-low.wav"))
        itemAppearSound = Gdx.audio.newSound(Gdx.files.internal("assets/ch08/breakout/swoosh.wav"))
        itemCollectSound = Gdx.audio.newSound(Gdx.files.internal("assets/ch08/breakout/pop.wav"))

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/ch08/breakout/Rollin-at-5.mp3"))
        backgroundMusic.isLooping = true
        backgroundMusic.volume = 0.50f
        backgroundMusic.play()
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (ball.isPaused) {
            ball.isPaused = false
            messageLabel.isVisible = false
        }
        return false
    }

    override fun update(dt: Float) {
        // get mouse position and move the paddle
        val mouseX = Gdx.input.x.toFloat()
        paddle.x = mouseX - paddle.width / 2
        paddle.boundToWorld()

        if (ball.isPaused) {
            ball.x = paddle.x + paddle.width / 2 - ball.width / 2
            ball.y = paddle.y + paddle.height / 2 + ball.height / 2
        }

        if (ball.overlaps(paddle)) {
            val ballCenterX = ball.x + ball.width / 2
            val paddlePercentHit = (ballCenterX - paddle.x) / paddle.width
            val bounceAngle = MathUtils.lerp(150f, 30f, paddlePercentHit)
            ball.motionAngle = bounceAngle

            bounceSound.play(volume)
        }

        for (wall in BaseActor.getList(mainStage, Wall::class)) {
            if (ball.overlaps(wall)) {
                ball.bounceOff(wall)
                wallBumpSound.play(volume)
            }
        }

        for (brick in BaseActor.getList(mainStage, Brick::class)) {
            if (ball.overlaps(brick)) {
                ball.bounceOff(brick)
                brick.remove()
                score += 100
                scoreLabel.setText("Score: $score")

                val spawnProbability = 20f // 20% probability
                if (MathUtils.random(0, 100) < spawnProbability) {
                    val i = Item(0f, 0f, mainStage)
                    i.centerAtActor(brick)
                    itemAppearSound.play(volume)
                }

                brickBumpSound.play(volume)
            }
        }

        if (ball.overlaps(paddle)) {
            val ballCenterX = ball.x + ball.width / 2
            val paddlePercentHit = (ballCenterX - paddle.x) / paddle.width // 0.0 to 1.0
            val bounceAngle = MathUtils.lerp(150f, 30f, paddlePercentHit) // interpolate
            ball.motionAngle = bounceAngle

            bounceSound.play(volume)
        }

        if (BaseActor.count(mainStage, Brick::class) == 0) {
            messageLabel.setText("you win!")
            messageLabel.color = Color.LIME
            messageLabel.isVisible = true
        }


        if (ball.y < -50 && BaseActor.count(mainStage, Brick::class) > 0) {
            ball.remove()

            if (balls > 0) {
                balls -= 1
                ballsLabel.setText("Balls: $balls")
                ball = Ball(0f, 0f, mainStage)

                messageLabel.setText("click to start")
                messageLabel.color = Color.CYAN
                messageLabel.isVisible = true
            } else {
                messageLabel.setText("game over")
                messageLabel.color = Color.RED
                messageLabel.isVisible = true
            }
        }

        for (item in BaseActor.getList(mainStage, Item::class)) {
            if (paddle.overlaps(item)) {
                val realItem = item as Item

                when (realItem.type) {
                    Item.Type.PADDLE_EXPAND -> paddle.width = paddle.width * 1.25f
                    Item.Type.PADDLE_SHRINK -> paddle.width = paddle.width * 0.80f
                    Item.Type.BALL_SPEED_UP -> ball.speed = ball.speed * 1.50f
                    Item.Type.BALL_SPEED_DOWN -> ball.speed = ball.speed * 0.90f
                }

                itemCollectSound.play(volume)

                // update boundary data
                paddle.setBoundaryRectangle()

                item.remove()

                itemCollectSound.play(volume)
            }
        }
    }
}