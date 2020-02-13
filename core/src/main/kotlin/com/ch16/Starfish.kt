package com.ch16

class Starfish(x: Float, y: Float, z: Float, s: Stage3D) : ObjModel(x, y, z, s) {

    override fun act(dt: Float) {
        super.act(dt)
        turnBy(90 * dt)
    }

    init {
        loadObjModel("assets/ch16/star.obj")
        setScale(3f, 1f, 3f)
        setBasePolygon()
    }
}