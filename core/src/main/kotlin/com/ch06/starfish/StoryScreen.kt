package com.ch06.starfish

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.ch06.starfish.cutscene.Scene
import com.ch06.starfish.cutscene.SceneActions
import com.ch06.starfish.cutscene.SceneSegment


class StoryScreen : BaseScreen() {
    private lateinit var scene: Scene
    private lateinit var continueKey: BaseActor

    override fun initialize() {
        val background = BaseActor(0f, 0f, mainStage)
        background.loadTexture("ch03/oceanside.png")
        background.setSize(800f, 600f)
        background.setOpacity(0f)
        BaseActor.setWorldBounds(background)

        val turtle = BaseActor(0f, 0f, mainStage)
        turtle.loadTexture("ch03/turtle-big.png")
        turtle.setPosition(-turtle.width, 0f)

        val dialogBox = DialogBox(0f, 0f, uiStage)
        dialogBox.setDialogSize(600f, 200f)
        dialogBox.setBackgroundColor(Color(0.6f, 0.6f, 0.8f, 1f))
        dialogBox.setFontScale(0.75f)
        dialogBox.isVisible = false

        uiTable.add(dialogBox).expandX().expandY().bottom()

        continueKey = BaseActor(0f, 0f, uiStage)
        continueKey.loadTexture("ch03/key-C.png")
        continueKey.setSize(32f, 32f)
        continueKey.isVisible = false

        dialogBox.addActor(continueKey)
        continueKey.setPosition(dialogBox.width - continueKey.width - 10f, 10f)

        scene = Scene()
        mainStage.addActor(scene)

        scene.addSegment(SceneSegment(background, Actions.fadeIn(1f)))
        scene.addSegment(SceneSegment(turtle, SceneActions.moveToScreenCenter(2f)))
        scene.addSegment(SceneSegment(dialogBox, Actions.show()))

        scene.addSegment(SceneSegment(dialogBox,
                                      SceneActions.setText("I want to be the very best... Starfish Collector!")))

        scene.addSegment(SceneSegment(continueKey, Actions.show()))
        scene.addSegment(SceneSegment(background, SceneActions.pause()))
        scene.addSegment(SceneSegment(continueKey, Actions.hide()))

        scene.addSegment(SceneSegment(dialogBox,
                                      SceneActions.setText("I've got to collect them all!")))

        scene.addSegment(SceneSegment(continueKey, Actions.show()))
        scene.addSegment(SceneSegment(background, SceneActions.pause()))
        scene.addSegment(SceneSegment(continueKey, Actions.hide()))

        scene.addSegments(
                SceneSegment(dialogBox, Actions.hide()),
                SceneSegment(turtle, SceneActions.moveToOutsideRight(1f)),
                SceneSegment(background, Actions.fadeOut(1f))
        )

        scene.start()
    }

    override fun update(dt: Float) {
        if (scene.isSceneFinished)
            BaseGame.setActiveScreen(LevelScreen())
    }

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Keys.C && continueKey.isVisible)
            scene.loadNextSegment()

        return false
    }
}