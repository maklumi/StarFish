package com.ch13.touchscreen

import com.TilemapActor
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.MapProperties
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.ch06.starfish.*

class LevelScreen : BaseTouchScreen() {
    private lateinit var touchpad: Touchpad
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

        BaseActor.setWorldBounds(1000f, 900f)

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
        uiTable.add()

        dialogBox = DialogBox(0f, 0f, uiStage)
        dialogBox.setBackgroundColor(Color.TAN)
        dialogBox.setFontColor(Color.BROWN)
        dialogBox.setDialogSize(600f, 100f)
        dialogBox.setFontScale(0.80f)
        dialogBox.alignCenter()
        dialogBox.isVisible = false

        uiTable.row()
        uiTable.add(dialogBox).colspan(4)

        waterDrop = Gdx.audio.newSound(Gdx.files.internal("ch03/Water_Drop.ogg"))
        instrumental = Gdx.audio.newMusic(Gdx.files.internal("ch03/Master_of_the_Feast.ogg"))
        oceanSurf = Gdx.audio.newMusic(Gdx.files.internal("ch03/Ocean_Waves.ogg"))

        instrumental.isLooping = true
        instrumental.volume = audioVolume
        instrumental.play()
        oceanSurf.isLooping = true
        oceanSurf.volume = audioVolume
        oceanSurf.play()

        // touch control
        Gdx.graphics.setWindowedMode(800, 600 + 200)
        initializeControlArea()
        val controlBackground = BaseActor(0f, 0f, controlStage)
        controlBackground.loadTexture("assets/ch13/pixels.jpg")

        // on-screen touchpad
        val touchStyle = TouchpadStyle()

        val padKnobTex = Texture(Gdx.files.internal("ch13/joystick-knob.png"))
        val padKnobReg = TextureRegion(padKnobTex)
        touchStyle.knob = TextureRegionDrawable(padKnobReg)

        val padBackTex = Texture(Gdx.files.internal("ch13/joystick-background.png"))
        val padBackReg = TextureRegion(padBackTex)
        touchStyle.background = TextureRegionDrawable(padBackReg)

        // deadZoneRadius is measured in pixels
        touchpad = Touchpad(5f, touchStyle)

        controlTable.toFront()
        controlTable.pad(50f)
        controlTable.add().colspan(3).height(600f)
        controlTable.row()
        controlTable.add(touchpad)
        controlTable.add().expandX()
        controlTable.add(restartButton)

    }

    override fun update(dt: Float) {
        // turtle speed and direction
        val direction = Vector2(touchpad.knobPercentX, touchpad.knobPercentY)
        val length = direction.len()
        if (length > 0) {
            turtle.speed = 100 * length
            turtle.motionAngle = direction.angle()
        }

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

}