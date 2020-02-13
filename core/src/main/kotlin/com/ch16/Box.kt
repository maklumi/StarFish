package com.ch16

import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.badlogic.gdx.math.Vector3

class Box(x: Float, y: Float, z: Float, s: Stage3D) : BaseActor3D(x, y, z, s) {
    init {
        val modelBuilder = ModelBuilder()
        val boxMaterial = Material()
        val usageCode = VertexAttributes.Usage.Position + VertexAttributes.Usage.ColorPacked + VertexAttributes.Usage.Normal + VertexAttributes.Usage.TextureCoordinates
        val boxModel = modelBuilder.createBox(1f, 1f, 1f, boxMaterial, usageCode.toLong())
        val position = Vector3(0f, 0f, 0f)
        setModelInstance(ModelInstance(boxModel, position))
    }
}