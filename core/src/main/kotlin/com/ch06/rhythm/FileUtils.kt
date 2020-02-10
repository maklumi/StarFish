package com.ch06.rhythm

import com.badlogic.gdx.files.FileHandle
import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.stage.FileChooser
import java.io.File

object FileUtils {
    private var finished: Boolean = false
    private var fileHandle: FileHandle? = null
    private const val openDialog = 1
    private const val saveDialog = 2

    fun showOpenDialog(): FileHandle? {
        return showDialog(openDialog)
    }

    fun showSaveDialog(): FileHandle? {
        return showDialog(saveDialog)
    }

    private fun showDialog(dialogType: Int): FileHandle? {
        JFXPanel()

        finished = false

        Platform.runLater {
            val fileChooser = FileChooser()
            fileChooser.initialDirectory = File(System.getProperty("user.dir"))
            val file: File? = if (dialogType == openDialog)
                fileChooser.showOpenDialog(null)
            else
                fileChooser.showSaveDialog(null)

            if (file != null) {
                fileHandle = FileHandle(file)
                finished = true
            } else
                fileHandle = null

            finished = true
        }

        while (finished) {
            // waits for FileChooser window to close
        }

        println("done ------------")
        return fileHandle
    }
}
