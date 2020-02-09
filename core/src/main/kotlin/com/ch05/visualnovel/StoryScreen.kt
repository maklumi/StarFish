package com.ch05.visualnovel

import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.ch05.starfish.cutscene.Scene
import com.ch05.starfish.cutscene.SceneSegment


class StoryScreen : BaseScreen() {

    private lateinit var scene: Scene
    private lateinit var background: Background
    private lateinit var kelsoe: Kelsoe
    private lateinit var dialogBox: DialogBox
    private lateinit var continueKey: BaseActor
    private lateinit var buttonTable: Table
    private lateinit var theEnd: BaseActor

    override fun initialize() {
        background = Background(0f, 0f, mainStage)
        background.setOpacity(0f)
        BaseActor.setWorldBounds(background)

        kelsoe = Kelsoe(0f, 0f, mainStage)

        dialogBox = DialogBox(0f, 0f, uiStage)
        dialogBox.setDialogSize(600f, 150f)
        dialogBox.setBackgroundColor(Color(0.2f, 0.2f, 0.2f, 0.5f))
        dialogBox.setFontColor(Color.WHITE)
        dialogBox.setFontScale(0.8f)
        dialogBox.isVisible = false

        continueKey = BaseActor(0f, 0f, uiStage)
        continueKey.loadTexture("ch05/homework/key-C.png")
        continueKey.setSize(32f, 32f)
        continueKey.isVisible = false

        dialogBox.addActor(continueKey)
        continueKey.setPosition(dialogBox.width - continueKey.width, 0f)

        buttonTable = Table()
        buttonTable.isVisible = false

        uiTable.add().expandY()
        uiTable.row()
        uiTable.add(buttonTable)
        uiTable.row()
        uiTable.add(dialogBox)

        // this is being added to the mainStage
        //  so that it does not block access to the buttons
        theEnd = BaseActor(0f, 0f, mainStage)
        theEnd.loadTexture("ch05/homework/the-end.png")
        theEnd.centerAtActor(background)
        theEnd.setScale(2f)
        theEnd.setOpacity(0f)

        scene = Scene()
        mainStage.addActor(scene)

        hallway()
    }

    private fun addTextSequence(s: String) {
        scene.addSegment(SceneSegment(dialogBox, SceneActions.typewriter(s)))
        scene.addSegment(SceneSegment(continueKey, Actions.show()))
        scene.addSegment(SceneSegment(background, SceneActions.pause()))
        scene.addSegment(SceneSegment(continueKey, Actions.hide()))
    }

    private fun hallway() {
        scene.clearSegments()

        background.setAnimation(background.hallway)
        dialogBox.setText("")
        kelsoe.addAction(SceneActions.moveToOutsideLeft(0f))

        scene.addSegment(SceneSegment(background, Actions.fadeIn(1f)))
        scene.addSegment(SceneSegment(kelsoe, SceneActions.moveToScreenCenter(1f)))
        scene.addSegment(SceneSegment(dialogBox, Actions.show()))

        addTextSequence("My name is Kelsoe Kismet. I am a student at Aureus Ludus Academy.")
        addTextSequence("I can be a little forgetful sometimes. Right now, I'm looking for my homework.")

        scene.addSegment(SceneSegment(dialogBox, Actions.hide()))
        scene.addSegment(SceneSegment(kelsoe, SceneActions.moveToOutsideRight(1f)))
        scene.addSegment(SceneSegment(background, Actions.fadeOut(1f)))

        scene.addSegment(SceneSegment(background, Actions.run { classroom() }))

        scene.start()
    }

    private fun classroom() {
        scene.clearSegments()

        background.setAnimation(background.classroom)
        dialogBox.setText("")
        kelsoe.addAction(SceneActions.moveToOutsideLeft(0f))

        scene.addSegment(SceneSegment(background, Actions.fadeIn(1f)))
        scene.addSegment(SceneSegment(kelsoe, SceneActions.moveToScreenCenter(1f)))
        scene.addSegment(SceneSegment(dialogBox, Actions.show()))

        addTextSequence("This is my classroom. My homework isn't here, though.")
        addTextSequence("Where should I look for my homework next?")

        scene.addSegment(SceneSegment(buttonTable, Actions.show()))

        // set up options
        val scienceLabButton = TextButton("Look in the Science Lab", BaseGame.textButtonStyle)
        scienceLabButton.addListener scienceLabButton@{ e: Event ->
            if (e !is InputEvent || e.type != Type.touchDown)
                return@scienceLabButton false

            scene.addSegment(SceneSegment(buttonTable, Actions.hide()))
            addTextSequence("That's a great idea. I'll check the science lab.")
            scene.addSegment(SceneSegment(dialogBox, Actions.hide()))
            scene.addSegment(SceneSegment(kelsoe, SceneActions.moveToOutsideLeft(1f)))
            scene.addSegment(SceneSegment(background, Actions.fadeOut(1f)))
            scene.addSegment(SceneSegment(background, Actions.run { scienceLab() }))

            false
        }

        val libraryButton = TextButton("Look in the Library", BaseGame.textButtonStyle)
        libraryButton.addListener libraryButton@{ e: Event ->
            if (e !is InputEvent || e.type != Type.touchDown)
                return@libraryButton false

            scene.addSegment(SceneSegment(buttonTable, Actions.hide()))
            addTextSequence("That's a great idea. Maybe I left it in the library.")
            scene.addSegment(SceneSegment(dialogBox, Actions.hide()))
            scene.addSegment(SceneSegment(kelsoe, SceneActions.moveToOutsideLeft(1f)))
            scene.addSegment(SceneSegment(background, Actions.fadeOut(1f)))
            scene.addSegment(SceneSegment(background, Actions.run { library() }))

            false
        }

        buttonTable.clearChildren()
        buttonTable.add(scienceLabButton)
        buttonTable.row()
        buttonTable.add(libraryButton)

        scene.start()
    }

    private fun scienceLab() {
        scene.clearSegments()

        background.setAnimation(background.scienceLab)
        dialogBox.setText("")
        kelsoe.addAction(SceneActions.moveToOutsideLeft(0f))

        scene.addSegment(SceneSegment(background, Actions.fadeIn(1f)))
        scene.addSegment(SceneSegment(kelsoe, SceneActions.moveToScreenCenter(1f)))
        scene.addSegment(SceneSegment(dialogBox, Actions.show()))

        addTextSequence("This is the science lab.")
        scene.addSegment(SceneSegment(kelsoe, SceneActions.setAnimation(kelsoe.sad)))
        addTextSequence("My homework isn't here, though.")
        scene.addSegment(SceneSegment(kelsoe, SceneActions.setAnimation(kelsoe.normal)))
        addTextSequence("Now where should I go?")

        scene.addSegment(SceneSegment(buttonTable, Actions.show()))

        // set up options
        val classroomButton = TextButton("Return to the Classroom", BaseGame.textButtonStyle)
        classroomButton.addListener classroomButton@{ e: Event ->
            if (e !is InputEvent || e.type != Type.touchDown)
                return@classroomButton false

            scene.addSegment(SceneSegment(buttonTable, Actions.hide()))
            addTextSequence("Maybe someone found it and put it in the classroom. I'll go check.")
            scene.addSegment(SceneSegment(dialogBox, Actions.hide()))
            scene.addSegment(SceneSegment(kelsoe, SceneActions.moveToOutsideRight(1f)))
            scene.addSegment(SceneSegment(background, Actions.fadeOut(1f)))
            scene.addSegment(SceneSegment(background, Actions.run { classroom() }))

            false
        }

        val libraryButton = TextButton("Look in the Library", BaseGame.textButtonStyle)
        libraryButton.addListener libraryButton@{ e: Event ->
            if (e !is InputEvent || e.type != Type.touchDown)
                return@libraryButton false

            scene.addSegment(SceneSegment(buttonTable, Actions.hide()))
            addTextSequence("That's a great idea. Maybe I left it in the library.")
            scene.addSegment(SceneSegment(dialogBox, Actions.hide()))
            scene.addSegment(SceneSegment(kelsoe, SceneActions.moveToOutsideRight(1f)))
            scene.addSegment(SceneSegment(background, Actions.fadeOut(1f)))
            scene.addSegment(SceneSegment(background, Actions.run { library() }))

            false
        }

        buttonTable.clearChildren()
        buttonTable.add(classroomButton)
        buttonTable.row()
        buttonTable.add(libraryButton)

        scene.start()
    }


    private fun library() {
        scene.clearSegments()

        background.setAnimation(background.library)
        dialogBox.setText("")
        kelsoe.addAction(SceneActions.moveToOutsideLeft(0f))

        scene.addSegment(SceneSegment(background, Actions.fadeIn(1f)))
        scene.addSegment(SceneSegment(kelsoe, SceneActions.moveToScreenCenter(1f)))
        scene.addSegment(SceneSegment(dialogBox, Actions.show()))

        addTextSequence("This is the library.")
        addTextSequence("Let me check the table where I was working earlier...")
        scene.addSegment(SceneSegment(kelsoe, SceneActions.setAnimation(kelsoe.lookRight)))
        scene.addSegment(SceneSegment(kelsoe, SceneActions.moveToScreenRight(2f)))
        scene.addSegment(SceneSegment(kelsoe, SceneActions.setAnimation(kelsoe.normal)))
        addTextSequence("Aha! Here it is!")
        scene.addSegment(SceneSegment(kelsoe, SceneActions.moveToScreenCenter(0.5f)))
        addTextSequence("Thanks for helping me find it!")
        scene.addSegment(SceneSegment(dialogBox, Actions.hide()))

        scene.addSegment(SceneSegment(theEnd, Actions.fadeIn(4f)))

        scene.addSegment(SceneSegment(background, Actions.delay(10f)))
        scene.addSegment(SceneSegment(background, Actions.run { BaseGame.setActiveScreen(MenuScreen()) }))
        scene.start()
    }

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Input.Keys.C) // && continueKey.isVisible() )
            scene.loadNextSegment()

        return false
    }

    override fun update(dt: Float) {
    }
}