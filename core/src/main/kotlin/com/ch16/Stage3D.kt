package com.ch16

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
import com.badlogic.gdx.math.Vector3
import java.util.*

class Stage3D {
    private val environment = Environment()
    private val camera = PerspectiveCamera(67f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    private val modelBatch = ModelBatch()
    val actors: ArrayList<BaseActor3D> = ArrayList()

    init {
        environment.set(ColorAttribute(ColorAttribute.AmbientLight, 0.7f, 0.7f, 0.7f, 1f))
        val dLight = DirectionalLight()
        val lightColor = Color(0.9f, 0.9f, 0.9f, 1f)
        val lightVector = Vector3(-1.0f, -0.75f, -0.25f)
        dLight[lightColor] = lightVector
        environment.add(dLight)
        camera.position[10f, 10f] = 10f
        camera.lookAt(0f, 0f, 0f)
        camera.near = 1f
        camera.far = 1000f
        camera.update()
    }

    fun act(dt: Float) {
        camera.update()
        for (ba in actors) ba.act(dt)
    }

    fun draw() {
        modelBatch.begin(camera)
        for (ba in actors) ba.draw(modelBatch, environment)
        modelBatch.end()
    }

    fun addActor(ba: BaseActor3D) {
        actors.add(ba)
    }

    fun removeActor(ba: BaseActor3D?) {
        actors.remove(ba)
    }

    fun setCameraPosition(x: Float, y: Float, z: Float) {
        camera.position[x, y] = z
    }

    fun setCameraPosition(v: Vector3?) {
        camera.position.set(v)
    }

    fun moveCamera(x: Float, y: Float, z: Float) {
        camera.position.add(x, y, z)
    }

    fun moveCamera(v: Vector3?) {
        camera.position.add(v)
    }

    fun moveCameraForward(dist: Float) {
        val forward = Vector3(camera.direction.x, 0f, camera.direction.z).nor()
        moveCamera(forward.scl(dist))
    }

    fun moveCameraRight(dist: Float) {
        val right = Vector3(camera.direction.z, 0f, -camera.direction.x).nor()
        moveCamera(right.scl(dist))
    }

    fun moveCameraUp(dist: Float) {
        moveCamera(0f, dist, 0f)
    }

    fun setCameraDirection(v: Vector3?) {
        camera.lookAt(v)
        camera.up[0f, 1f] = 0f
    }

    fun setCameraDirection(x: Float, y: Float, z: Float) {
        setCameraDirection(Vector3(x, y, z))
    }

    fun turnCamera(angle: Float) {
        camera.rotate(Vector3.Y, -angle)
    }

    fun tiltCamera(angle: Float) {
        val side = Vector3(camera.direction.z, 0f, -camera.direction.x)
        camera.direction.rotate(side, angle)
    }


}