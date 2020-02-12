package com.ch15.spacerock

import com.BaseScreen
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.ch04.BaseActor
import com.ch04.Explosion
import com.ch04.Laser
import com.ch04.Rock


class LevelScreen : BaseScreen() {
    private lateinit var spaceship: Spaceship
    private var gameOver: Boolean = false

    override fun initialize() {
        val space = BaseActor(0f, 0f, mainStage)
        space.loadTexture("ch04/space.png")
        space.setSize(800f, 600f)
        BaseActor.setWorldBounds(space)

        spaceship = Spaceship(400f, 300f, mainStage)

        Rock(600f, 500f, mainStage)
        Rock(600f, 300f, mainStage)
        Rock(600f, 100f, mainStage)
        Rock(400f, 100f, mainStage)
        Rock(200f, 100f, mainStage)
        Rock(200f, 300f, mainStage)
        Rock(200f, 500f, mainStage)
        Rock(400f, 500f, mainStage)

        gameOver = false
    }

    override fun update(dt: Float) {

        for (rockActor in BaseActor.getList(mainStage, Rock::class)) {
            if (rockActor.overlaps(spaceship)) {
                if (spaceship.shieldPower <= 0) {

                    val boom = ExplosionEffect()
                    boom.centerAtActor(spaceship)
                    boom.start()
                    mainStage.addActor(boom)

                    spaceship.remove()
                    spaceship.setPosition(-1000f, -1000f)

                    val messageLose = BaseActor(0f, 0f, uiStage)
                    messageLose.loadTexture("ch04/message-lose.png")
                    messageLose.centerAtPosition(400f, 300f)
                    messageLose.setOpacity(0f)
                    messageLose.addAction(Actions.fadeIn(1f))
                    gameOver = true
                } else {
                    spaceship.shieldPower -= 34
                    val boom = Explosion(0f, 0f, mainStage)
                    boom.centerAtActor(rockActor)
                    rockActor.remove()
                }

            }

            for (laserActor in BaseActor.getList(mainStage, Laser::class)) {
                if (laserActor.overlaps(rockActor)) {

                    val boom = ExplosionEffect()
                    boom.centerAtActor(rockActor)
                    boom.start()
                    mainStage.addActor(boom)

                    laserActor.remove()
                    rockActor.remove()
                }
            }
        }

        if (!gameOver && BaseActor.count(mainStage, Rock::class) == 0) {
            val messageWin = BaseActor(0f, 0f, uiStage)
            messageWin.loadTexture("ch04/message-win.png")
            messageWin.centerAtPosition(400f, 300f)
            messageWin.setOpacity(0f)
            messageWin.addAction(Actions.fadeIn(1f))
            gameOver = true
        }

    }

    // override default InputProcessor methods
    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Keys.SPACE)
            spaceship.shoot()

        if (keycode == Keys.X)
            spaceship.warp()

        return false
    }
}