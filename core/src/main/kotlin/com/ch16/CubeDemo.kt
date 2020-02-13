package com.ch16

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.badlogic.gdx.math.Vector3

class CubeDemo : ApplicationListener {
    private lateinit var environment: Environment
    private lateinit var camera: PerspectiveCamera
    private lateinit var modelBatch: ModelBatch
    private lateinit var boxInstance: ModelInstance

    override fun create() {
        environment = Environment()
        environment.set(ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f))
        val dLight = DirectionalLight()
        val lightColor = Color(0.75f, 0.75f, 0.75f, 1f)
        val lightVector = Vector3(-1.0f, -0.75f, -0.25f)
        dLight[lightColor] = lightVector
        environment.add(dLight)
        camera = PerspectiveCamera(67f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        camera.position[10f, 10f] = 10f
        camera.lookAt(0f, 0f, 0f)
        camera.near = 1f
        camera.far = 300f
        camera.update()
        val modelBuilder = ModelBuilder()
        val boxMaterial = Material()
        boxMaterial.set(ColorAttribute.createDiffuse(Color.BLUE))
        val usageCode = VertexAttributes.Usage.Position + VertexAttributes.Usage.ColorPacked + VertexAttributes.Usage.Normal
        val boxModel = modelBuilder.createBox(5f, 5f, 5f, boxMaterial, usageCode.toLong())
        boxInstance = ModelInstance(boxModel)
        modelBatch = ModelBatch()
    }

    override fun render() {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glViewport(0, 0, Gdx.graphics.width, Gdx.graphics.height)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
        modelBatch.begin(camera)
        modelBatch.render(boxInstance, environment)
        modelBatch.end()
    }

    override fun dispose() {}
    override fun resize(width: Int, height: Int) {}
    override fun pause() {}
    override fun resume() {}
}