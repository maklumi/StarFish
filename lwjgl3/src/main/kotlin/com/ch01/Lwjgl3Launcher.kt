package com.ch01

import com.badlogic.gdx.Game
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.ch02.StarfishCollectorAlpha
import com.ch02.StarfishCollectorBeta
import com.ch03.StarfishCollector
import com.ch03.StarfishGame
import java.util.*

/** Launches the desktop (LWJGL3) application.  */
object Lwjgl3Launcher {
    @JvmStatic
    fun main(args: Array<String>) {
        createApplication()
    }

    private val applications: ArrayList<Game> = arrayListOf(
            StarfishGame(),
            StarfishCollector(),
            StarfishCollectorBeta(),
            StarfishCollectorAlpha(),
            HelloWorldImage()
    )

    private fun createApplication(): Lwjgl3Application {
        return Lwjgl3Application(applications.first(), defaultConfiguration)
    }

    private val defaultConfiguration: Lwjgl3ApplicationConfiguration
        get() {
            val configuration = Lwjgl3ApplicationConfiguration()
            configuration.setTitle("StarFish")
            configuration.setWindowedMode(800, 600)
            configuration.setWindowPosition(5, 1030 - 600)
            configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png")
            return configuration
        }
}