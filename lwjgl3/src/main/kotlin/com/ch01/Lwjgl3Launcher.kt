package com.ch01

import com.badlogic.gdx.Game
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import java.util.*

/** Launches the desktop (LWJGL3) application.  */
object Lwjgl3Launcher {
    @JvmStatic
    fun main(args: Array<String>) {
        createApplication()
    }

    private val applications: ArrayList<Game> = arrayListOf(
            com.ch15.shaders.StarfishGame(),
            com.ch15.spacerock.SpaceGame(), //800,600
            com.ch14.MazeGame(), // 768,700
            com.ch13.touchscreen.StarfishGame(), //800, 800
            com.ch12.zelda.LegendOfZelda(), // 800,600
            com.ch11.mariobros.MarioBrosGame(),
            com.ch10.brickbreaker.BreakoutGame(),
            com.ch10.starfish.StarfishGame(),
            com.ch09.solitaire.SolitaireCardGame(),
            com.ch09.jigsaw.JigsawPuzzleGame(),
            com.ch08.brickbreaker.BreakoutGame(),
            com.ch07.sidescrolling.PlaneDodgerGame(),
            com.ch06.rhythm.RhythmGame(),
            com.ch06.rhythm.RecorderGame(),
            com.ch06.starfish.StarfishGame(),
            com.ch05.visualnovel.HomeworkGame(),
            com.ch05.starfish.StarfishGame(),
            com.ch04.SpaceGame(),
            com.ch03.StarfishGame(),
            com.ch03.StarfishCollector(),
            com.ch02.StarfishCollectorBeta(),
            com.ch02.StarfishCollectorAlpha(),
            HelloWorldImage()
    )

    private fun createApplication(): Lwjgl3Application {
        return Lwjgl3Application(applications.first(), defaultConfiguration)
    }

    private val defaultConfiguration: Lwjgl3ApplicationConfiguration
        get() {
            val configuration = Lwjgl3ApplicationConfiguration()
            configuration.setTitle(applications.first()::class.java.simpleName)
            configuration.setWindowedMode(800, 600)
            configuration.setWindowPosition(5, 1030 - 600)
            configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png")
            return configuration
        }
}