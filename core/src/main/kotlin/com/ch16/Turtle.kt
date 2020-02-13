package com.ch16

class Turtle(x: Float, y: Float, z: Float, s: Stage3D) : ObjModel(x, y, z, s) {
    init {
        loadObjModel("assets/ch16/turtle.obj")
        setBasePolygon()
    }
}