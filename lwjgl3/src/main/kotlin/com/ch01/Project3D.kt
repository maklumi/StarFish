package com.ch01

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.ch16.CubeDemo
import com.ch16.MoveDemo
import com.ch16.StarfishCollector3DGame

object Project3D {

    @JvmStatic
    fun main(args: Array<String>) {
        createApplication()
    }

    private fun createApplication(): Lwjgl3Application {
        return Lwjgl3Application(StarfishCollector3DGame(), defaultConfiguration)
//        return Lwjgl3Application(MoveDemo(), defaultConfiguration)
//        return Lwjgl3Application(CubeDemo(), defaultConfiguration)
    }

    private val defaultConfiguration: Lwjgl3ApplicationConfiguration
        get() {
            val configuration = Lwjgl3ApplicationConfiguration()
            configuration.setTitle("Project 3D")
            configuration.setWindowedMode(800, 600)
            configuration.setWindowPosition(5, 1030 - 600)
            configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png")
            return configuration
        }
}