package com.ch16

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute
import com.badlogic.gdx.math.*
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector
import com.badlogic.gdx.math.collision.BoundingBox
import java.util.*
import kotlin.reflect.KClass

open class BaseActor3D(x: Float, y: Float, z: Float, private val s: Stage3D) {

    private lateinit var modelData: ModelInstance
    val position = Vector3(x, y, z)
    protected val rotation = Quaternion()
    private val scale = Vector3(1f, 1f, 1f)
    private lateinit var boundingPolygon: Polygon
    protected var stage: Stage3D = s


    fun setModelInstance(m: ModelInstance) {
        s.addActor(this)

        modelData = m
    }

    private fun calculateTransform(): Matrix4 {
        return Matrix4(position, rotation, scale)
    }

    open fun act(dt: Float) {
        modelData.transform.set(calculateTransform())
    }

    fun draw(batch: ModelBatch, env: Environment?) {
        batch.render(modelData, env)
    }

    fun setColor(c: Color?) {
        modelData.materials.forEach { m ->
            m.set(ColorAttribute.createDiffuse(c))
        }
    }

    fun loadTexture(fileName: String?) {
        val tex = Texture(Gdx.files.internal(fileName), true)
        tex.setFilter(TextureFilter.Linear, TextureFilter.Linear)
        modelData.materials.forEach { m ->
            m.set(TextureAttribute.createDiffuse(tex))
        }
    }

    fun setPosition(v: Vector3?) {
        position.set(v)
    }

    fun setPosition(x: Float, y: Float, z: Float) {
        position[x, y] = z
    }

    fun moveBy(v: Vector3?) {
        position.add(v)
    }

    fun moveBy(x: Float, y: Float, z: Float) {
        moveBy(Vector3(x, y, z))
    }

    fun moveForward(dist: Float) {
        moveBy(rotation.transform(Vector3(0f, 0f, -1f)).scl(dist))
    }

    fun moveUp(dist: Float) {
        moveBy(rotation.transform(Vector3(0f, 1f, 0f)).scl(dist))
    }

    fun moveRight(dist: Float) {
        moveBy(rotation.transform(Vector3(1f, 0f, 0f)).scl(dist))
    }

    var turnAngle: Float
        get() = rotation.getAngleAround(0f, -1f, 0f)
        set(degrees) {
            rotation.set(Quaternion(Vector3.Y, degrees))
        }

    fun turnBy(degrees: Float) {
        rotation.mul(Quaternion(Vector3.Y, -degrees))
    }

    fun setScale(x: Float, y: Float, z: Float) {
        scale[x, y] = z
    }

    // 2D collision detection
    fun setBaseRectangle() {
        val modelBounds = modelData.calculateBoundingBox(BoundingBox())
        val max = modelBounds.max
        val min = modelBounds.min
        val vertices = floatArrayOf(max.x, max.z, min.x, max.z, min.x, min.z, max.x, min.z)
        boundingPolygon = Polygon(vertices)
        boundingPolygon.setOrigin(0f, 0f)
    }

    fun setBasePolygon() {
        val modelBounds = modelData.calculateBoundingBox(BoundingBox())
        val max = modelBounds.max
        val min = modelBounds.min
        val a = 0.75f // offset amount.
        val vertices = floatArrayOf(max.x, 0f, a * max.x, a * max.z, 0f, max.z, a * min.x, a * max.z,
                                    min.x, 0f, a * min.x, a * min.z, 0f, min.z, a * max.x, a * min.z)
        boundingPolygon = Polygon(vertices)
        boundingPolygon.setOrigin(0f, 0f)
    }

    private val boundaryPolygon: Polygon
        get() {
            boundingPolygon.setPosition(position.x, position.z)
            boundingPolygon.rotation = turnAngle
            boundingPolygon.setScale(scale.x, scale.z)
            return boundingPolygon
        }

    fun overlaps(other: BaseActor3D): Boolean {
        val poly1 = boundaryPolygon
        val poly2 = other.boundaryPolygon
        if (!poly1.boundingRectangle.overlaps(poly2.boundingRectangle)) return false
        val mtv = MinimumTranslationVector()
        return Intersector.overlapConvexPolygons(poly1, poly2, mtv)
    }

    fun preventOverlap(other: BaseActor3D) {
        val poly1 = boundaryPolygon
        val poly2 = other.boundaryPolygon
        // initial test to improve performance
        if (!poly1.boundingRectangle.overlaps(poly2.boundingRectangle)) return
        val mtv = MinimumTranslationVector()
        val polygonOverlap = Intersector.overlapConvexPolygons(poly1, poly2, mtv)
        if (polygonOverlap) this.moveBy(mtv.normal.x * mtv.depth, 0f, mtv.normal.y * mtv.depth)
    }

    fun remove() {
        stage.removeActor(this)
    }

    companion object {
        @JvmStatic
        fun getList(stage: Stage3D, className: KClass<out BaseActor3D>): ArrayList<BaseActor3D> {
            val list = ArrayList<BaseActor3D>()
            val theClass: Class<out BaseActor3D> = className.java
            for (ba3d in stage.actors) {
                if (theClass.isInstance(ba3d)) list.add(ba3d)
            }
            return list
        }

        fun count(stage: Stage3D, className: KClass<out BaseActor3D>): Int {
            return getList(stage, className).size
        }
    }

}