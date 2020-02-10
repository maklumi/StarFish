package com.ch12.zelda

import com.*
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label

class LevelScreen : BaseScreen() {

    private lateinit var hero: Hero
    private lateinit var sword: Sword

    private var health: Int = 0
    private var coins: Int = 0
    private var arrows: Int = 0
    private var gameOver: Boolean = false
    private lateinit var healthLabel: Label
    private lateinit var coinLabel: Label
    private lateinit var arrowLabel: Label
    private lateinit var messageLabel: Label
    private lateinit var dialogBox: DialogBox

    private lateinit var treasure: Treasure

    private lateinit var shopHeart: ShopHeart
    private lateinit var shopArrow: ShopArrow

    override fun initialize() {
        val tma = TilemapActor("assets/ch12/zelda/map2.tmx", mainStage)

        for (obj in tma.getRectangleList("Solid")) {
            val props = obj.properties
            Solid(props.get("x") as Float, props.get("y") as Float,
                  props.get("width") as Float, props.get("height") as Float,
                  mainStage)
        }

        val startPoint = tma.getRectangleList("Start")[0]
        val startProps = startPoint.properties
        hero = Hero(startProps.get("x") as Float, startProps.get("y") as Float, mainStage)

        sword = Sword(0f, 0f, mainStage)
        sword.isVisible = false

        for (obj in tma.getTileList("Bush")) {
            val props = obj.properties
            Bush(props.get("x") as Float, props.get("y") as Float, mainStage)
        }

        for (obj in tma.getTileList("Rock")) {
            val props = obj.properties
            Rock(props.get("x") as Float, props.get("y") as Float, mainStage)
        }

        for (obj in tma.getTileList("Coin")) {
            val props = obj.properties
            Coin(props.get("x") as Float, props.get("y") as Float, mainStage)
        }

        val treasureTile = tma.getTileList("Treasure")[0]
        val treasureProps = treasureTile.properties
        treasure = Treasure(treasureProps.get("x") as Float, treasureProps.get("y") as Float, mainStage)


        health = 3
        coins = 5
        arrows = 30
        healthLabel = Label(" x $health", BaseGame.labelStyle)
        healthLabel.color = Color.PINK
        coinLabel = Label(" x $coins", BaseGame.labelStyle)
        coinLabel.color = Color.GOLD
        arrowLabel = Label(" x $arrows", BaseGame.labelStyle)
        arrowLabel.color = Color.TAN
        messageLabel = Label("...", BaseGame.labelStyle)
        messageLabel.isVisible = false

        dialogBox = DialogBox(0f, 0f, uiStage)
        dialogBox.setBackgroundColor(Color.TAN)
        dialogBox.setFontColor(Color.BROWN)
        dialogBox.setDialogSize(600f, 100f)
        dialogBox.setFontScale(0.80f)
        dialogBox.alignCenter()
        dialogBox.isVisible = false

        val healthIcon = BaseActor(0f, 0f, uiStage)
        healthIcon.loadTexture("assets/ch12/zelda/heart-icon.png")
        val coinIcon = BaseActor(0f, 0f, uiStage)
        coinIcon.loadTexture("assets/ch12/zelda/coin-icon.png")
        val arrowIcon = BaseActor(0f, 0f, uiStage)
        arrowIcon.loadTexture("assets/ch12/zelda/arrow-icon.png")

        uiTable.pad(20f)
        uiTable.add(healthIcon)
        uiTable.add(healthLabel)
        uiTable.add().expandX()
        uiTable.add(coinIcon)
        uiTable.add(coinLabel)
        uiTable.add().expandX()
        uiTable.add(arrowIcon)
        uiTable.add(arrowLabel)
        uiTable.row()
        uiTable.add(messageLabel).colspan(8).expandX().expandY()
        uiTable.row()
        uiTable.add(dialogBox).colspan(8)
        uiTable.debug()

        for (obj in tma.getTileList("Flyer")) {
            val props = obj.properties
            Flyer(props.get("x") as Float, props.get("y") as Float, mainStage)
        }

        for (obj in tma.getTileList("NPC")) {
            val props = obj.properties
            val s = NPC(props.get("x") as Float, props.get("y") as Float, mainStage)
            s.id = props.get("id") as String
            s.text = props.get("text") as String
        }

        val shopHeartTile = tma.getTileList("ShopHeart")[0]
        val shopHeartProps = shopHeartTile.properties
        shopHeart = ShopHeart(shopHeartProps.get("x") as Float, shopHeartProps.get("y") as Float, mainStage)

        val shopArrowTile = tma.getTileList("ShopArrow")[0]
        val shopArrowProps = shopArrowTile.properties
        shopArrow = ShopArrow(shopArrowProps.get("x") as Float, shopArrowProps.get("y") as Float, mainStage)

        hero.toFront()
    }

    override fun update(dt: Float) {

        if (gameOver)
            return

        healthLabel.setText(" x $health")
        coinLabel.setText(" x $coins")
        arrowLabel.setText(" x $arrows")

        if (!sword.isVisible) {
            // hero movement controls
            if (Gdx.input.isKeyPressed(Keys.LEFT))
                hero.accelerateAtAngle(180f)
            if (Gdx.input.isKeyPressed(Keys.RIGHT))
                hero.accelerateAtAngle(0f)
            if (Gdx.input.isKeyPressed(Keys.UP))
                hero.accelerateAtAngle(90f)
            if (Gdx.input.isKeyPressed(Keys.DOWN))
                hero.accelerateAtAngle(270f)
        }

        for (solid in BaseActor.getList(mainStage, Solid::class)) {
            hero.preventOverlap(solid)

            for (flyer in BaseActor.getList(mainStage, Flyer::class)) {
                if (flyer.overlaps(solid)) {
                    flyer.preventOverlap(solid)
                    flyer.motionAngle = flyer.motionAngle + 180
                }
            }
        }

        if (sword.isVisible) {
            for (bush in BaseActor.getList(mainStage, Bush::class)) {
                if (sword.overlaps(bush))
                    bush.remove()
            }

            for (flyer in BaseActor.getList(mainStage, Flyer::class)) {
                if (sword.overlaps(flyer)) {
                    flyer.remove()
                    val coin = Coin(0f, 0f, mainStage)
                    coin.centerAtActor(flyer)
                    val smoke = Smoke(0f, 0f, mainStage)
                    smoke.centerAtActor(flyer)
                }
            }
        }

        for (coin in BaseActor.getList(mainStage, Coin::class)) {
            if (hero.overlaps(coin)) {
                coin.remove()
                coins++
            }
        }

        for (flyer in BaseActor.getList(mainStage, Flyer::class)) {
            // knock back
            if (hero.overlaps(flyer)) {
                hero.preventOverlap(flyer)
                flyer.motionAngle = flyer.motionAngle + 180
                val heroPosition = Vector2(hero.x, hero.y)
                val flyerPosition = Vector2(flyer.x, flyer.y)
                val hitVector = heroPosition.sub(flyerPosition)
                hero.motionAngle = hitVector.angle()
                hero.speed = 100f
                health--
            }
        }


        for (npcActor in BaseActor.getList(mainStage, NPC::class)) {
            val npc = npcActor as NPC

            hero.preventOverlap(npc)
            val nearby = hero.isWithinDistance(4f, npc)

            if (nearby && !npc.isViewing) {
                // check NPC ID for dynamic text
                if (npc.id == "Gatekeeper") {
                    val flyerCount = BaseActor.count(mainStage, Flyer::class)
                    var message = "Destroy the flyers and you can have the treasure. "
                    when {
                        flyerCount > 1 -> message += "There are $flyerCount left."
                        flyerCount == 1 -> message += "There is $flyerCount left."
                        else -> {
                            message = "The treasure is yours!"
                            npc.addAction(Actions.fadeOut(5.0f))
                            npc.addAction(Actions.after(Actions.moveBy(-10000f, -10000f)))
                        }
                    }

                    dialogBox.setText(message)
                } else {
                    dialogBox.setText(npc.text)
                }
                dialogBox.isVisible = true
                npc.isViewing = true
            }

            if (npc.isViewing && !nearby) {
                dialogBox.setText(" ")
                dialogBox.isVisible = false
                npc.isViewing = false
            }
        }

        if (hero.overlaps(treasure)) {
            messageLabel.setText("You win!")
            messageLabel.color = Color.LIME
            messageLabel.setFontScale(2f)
            messageLabel.isVisible = true
            treasure.remove()
            gameOver = true
        }

        if (health <= 0) {
            messageLabel.setText("Game over...")
            messageLabel.color = Color.RED
            messageLabel.setFontScale(2f)
            messageLabel.isVisible = true
            hero.remove()
            gameOver = true
        }

        for (arrow in BaseActor.getList(mainStage, Arrow::class)) {
            for (flyer in BaseActor.getList(mainStage, Flyer::class)) {
                if (arrow.overlaps(flyer)) {
                    flyer.remove()
                    arrow.remove()
                    val coin = Coin(0f, 0f, mainStage)
                    coin.centerAtActor(flyer)
                    val smoke = Smoke(0f, 0f, mainStage)
                    smoke.centerAtActor(flyer)
                }

            }

            for (solid in BaseActor.getList(mainStage, Solid::class)) {
                if (arrow.overlaps(solid)) {
                    arrow.preventOverlap(solid)
                    arrow.speed = 0f
                    arrow.addAction(Actions.fadeOut(0.5f))
                    arrow.addAction(Actions.after(Actions.removeActor()))
                }

            }
        }

    }

    private fun swingSword() {
        // visibility determines if sword is currently swinging
        if (sword.isVisible)
            return

        hero.speed = 0f

        val facingAngle = hero.facingAngle

        val offset = Vector2()
        when (facingAngle) {
            0f -> offset.set(0.50f, 0.20f)
            90f -> offset.set(0.65f, 0.50f)
            180f -> offset.set(0.40f, 0.20f)
            else -> offset.set(0.25f, 0.20f)    // facingAngle == 270f
        }

        sword.setPosition(hero.x, hero.y)
        sword.moveBy(offset.x * hero.width, offset.y * hero.height)

        val swordArc = 90f
        sword.rotation = facingAngle - swordArc / 2
        sword.originX = 0f

        sword.isVisible = true
        sword.addAction(Actions.rotateBy(swordArc, 0.25f))
        sword.addAction(Actions.after(Actions.visible(false)))

        // hero should appear in front of sword when facing north or west
        if (facingAngle == 90f || facingAngle == 180f)
            hero.toFront()
        else
            sword.toFront()
    }

    private fun shootArrow() {
        if (arrows <= 0)
            return

        arrows--

        val arrow = Arrow(0f, 0f, mainStage)
        arrow.centerAtActor(hero)
        arrow.rotation = hero.facingAngle
        arrow.motionAngle = hero.facingAngle
    }

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Keys.S)
            swingSword()
        if (keycode == Keys.A)
            shootArrow()
        if (keycode == Keys.B) {
            if (hero.overlaps(shopHeart) && coins >= 3) {
                coins -= 3
                health += 1
            }

            if (hero.overlaps(shopArrow) && coins >= 4) {
                coins -= 4
                arrows += 3
            }
        }
        return false
    }
}