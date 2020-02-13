package com.ch16

class Rock(x: Float, y: Float, z: Float, s: Stage3D) : ObjModel(x, y, z, s) {
    init {
        loadObjModel("assets/ch16/rock.obj")
        setBasePolygon()
        setScale(3f, 3f, 3f)
    }
}