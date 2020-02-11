package com.ch13.gamepad

import com.TilemapActor
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.MapProperties
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.ch06.starfish.*

class LevelScreen : BaseGamepadScreen() {
    private lateinit var turtle: Turtle
    private var win = false

    private lateinit var starfishLabel: Label
    private lateinit var dialogBox: DialogBox

    private var audioVolume = .1f
    private lateinit var waterDrop: Sound
    private lateinit var instrumental: Music
    private lateinit var oceanSurf: Music

    override fun initialize() {
        val tma = TilemapActor("assets/ch03/map.tmx", mainStage)

        for (obj in tma.getTileList("Rock")) {
            val props = obj.properties
            Rock(props.get("x") as Float, props.get("y") as Float, mainStage)
        }

        for (obj in tma.getTileList("Starfish")) {
            val props = obj.properties
            Starfish(props.get("x") as Float, props.get("y") as Float, mainStage)
        }

        for (obj in tma.getTileList("Sign")) {
            val props: MapProperties = obj.properties
            val s = Sign(props.get("x") as Float, props.get("y") as Float, mainStage)
            s.text = props.get("message") as String
        }

        val startPoint = tma.getRectangleList("Start")[0]
        val props = startPoint.properties

        turtle = Turtle(props.get("x") as Float, props["y"] as Float, mainStage)

        // ui
        starfishLabel = Label("Starfish Left:", BaseGame.labelStyle)
        starfishLabel.color = Color.CYAN

        val buttonTex = Texture(Gdx.files.internal("ch03/undo.png"))
        val buttonRegion = TextureRegion(buttonTex)
        val buttonStyle = Button.ButtonStyle()
        buttonStyle.up = TextureRegionDrawable(buttonRegion)

        val restartButton = Button(buttonStyle)
        restartButton.color = Color.CYAN

        restartButton.addListener restartButton@{ e: Event ->
            if (!isTouchDownEvent(e)) return@restartButton false
            instrumental.dispose()
            oceanSurf.dispose()
            BaseGame.setActiveScreen(LevelScreen())

            false
        }

        val buttonStyle2 = Button.ButtonStyle()
        val buttonTex2 = Texture(Gdx.files.internal("ch03/audio.png"))
        val buttonRegion2 = TextureRegion(buttonTex2)
        buttonStyle2.up = TextureRegionDrawable(buttonRegion2)

        val muteButton = Button(buttonStyle2)
        muteButton.color = Color.PURPLE

        muteButton.addListener muteButton@{ e: Event ->
            if (!isTouchDownEvent(e))
                return@muteButton false

//            audioVolume = if (audioVolume > 0) 0f else .1f
            audioVolume = 1 - audioVolume
            instrumental.volume = audioVolume
            oceanSurf.volume = audioVolume

            true
        }

        uiTable.pad(10f)
        uiTable.add(starfishLabel).top()
        uiTable.add().expandX().expandY()
        uiTable.add(muteButton).top()
        uiTable.add(restartButton).top()

        dialogBox = DialogBox(0f, 0f, uiStage)
        dialogBox.setBackgroundColor(Color.TAN)
        dialogBox.setFontColor(Color.BROWN)
        dialogBox.setDialogSize(600f, 100f)
        dialogBox.setFontScale(0.80f)
        dialogBox.alignCenter()
        dialogBox.isVisible = false

        uiTable.row()
        uiTable.add(dialogBox).colspan(4)
        uiTable.debug()

        waterDrop = Gdx.audio.newSound(Gdx.files.internal("ch03/Water_Drop.ogg"))
        instrumental = Gdx.audio.newMusic(Gdx.files.internal("ch03/Master_of_the_Feast.ogg"))
        oceanSurf = Gdx.audio.newMusic(Gdx.files.internal("ch03/Ocean_Waves.ogg"))

        instrumental.isLooping = true
        instrumental.volume = audioVolume
        instrumental.play()
        oceanSurf.isLooping = true
        oceanSurf.volume = audioVolume
        oceanSurf.play()
    }

    override fun update(dt: Float) {
        for (rockActor in BaseActor.getList(mainStage, Rock::class)) turtle.preventOverlap(rockActor)
        for (starfishActor in BaseActor.getList(mainStage, Starfish::class)) {
            val starfish = starfishActor as Starfish
            if (turtle.overlaps(starfish) && !starfish.collected) {
                waterDrop.play(audioVolume)

                starfish.collected = true
                starfish.clearActions()
                starfish.addAction(Actions.fadeOut(1f))
                starfish.addAction(Actions.after(Actions.removeActor()))

                val whirl = Whirlpool(0f, 0f, mainStage)
                whirl.centerAtActor(starfish)
                whirl.setOpacity(0.25f)
            }
        }
        if (BaseActor.count(mainStage, Starfish::class) == 0 && !win) {
            win = true
            val youWinMessage = BaseActor(0f, 0f, uiStage)
            youWinMessage.loadTexture("ch03/you-win.png")
            youWinMessage.centerAtPosition(400f, 300f)
            youWinMessage.setOpacity(0f)
            youWinMessage.addAction(Actions.delay(1f))
            youWinMessage.addAction(Actions.after(Actions.fadeIn(1f)))
        }

        starfishLabel.setText("Starfish Left: " + BaseActor.count(mainStage, Starfish::class))

        for (signActor in BaseActor.getList(mainStage, Sign::class)) {
            val sign = signActor as Sign

            turtle.preventOverlap(sign)
            val nearby = turtle.isWithinDistance(4f, sign)

            if (nearby && !sign.isViewing) {
                dialogBox.setText(sign.text)
                dialogBox.isVisible = true
                sign.isViewing = true
            }

            if (sign.isViewing && !nearby) {
                dialogBox.setText(" ")
                dialogBox.isVisible = false
                sign.isViewing = false
            }
        }
    }

    override fun buttonDown(controller: Controller, buttonCode: Int): Boolean {
        if (buttonCode == XBoxGamepad.BUTTON_BACK) BaseGame.setActiveScreen(LevelScreen())
        return false
    }
}