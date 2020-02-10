package com

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage


/**
 * Loads a Tiled map file (*.tmx), extends Actor to automatically render.
 */
class TilemapActor(filename: String, theStage: Stage) : Actor() {

    private val tiledMap: TiledMap = TmxMapLoader().load(filename)
    private val tiledCamera: OrthographicCamera = OrthographicCamera()
    private val tiledMapRenderer: OrthoCachedTiledMapRenderer = OrthoCachedTiledMapRenderer(tiledMap)

    /**
     * Initialize Tilemap created with the Tiled Map Editor.
     */
    init {
        // set up tile map, renderer, and camera
        val tileWidth = tiledMap.properties.get("tilewidth") as Int
        val tileHeight = tiledMap.properties.get("tileheight") as Int
        val numTilesHorizontal = tiledMap.properties.get("width") as Int
        val numTilesVertical = tiledMap.properties.get("height") as Int
        val mapWidth = tileWidth * numTilesHorizontal
        val mapHeight = tileHeight * numTilesVertical

        tiledMapRenderer.setBlending(true)
        tiledCamera.setToOrtho(false, windowWidth.toFloat(), windowHeight.toFloat())
        tiledCamera.update()

        // by adding object to Stage, can be drawn automatically
        theStage.addActor(this)

        // in theory, a solid boundary should be placed around the edge of the screen,
        //  but just in case, this map can be used to set boundaries
        BaseActor.setWorldBounds(mapWidth.toFloat(), mapHeight.toFloat())
    }

    /**
     * Search the map layers for Rectangle Objects that contain a property (key) called "name" with associated value propertyName.
     * Typically used to store non-actor information such as SpawnPoint locations or dimensions of Solid objects.
     * Retrieve data as object, then cast to desired type: for example, float w = (float)obj.getProperties().get("width").
     */
    fun getRectangleList(propertyName: String): ArrayList<MapObject> {

        val list = ArrayList<MapObject>()

        for (layer in tiledMap.layers) {
            for (obj in layer.objects) {
                if (obj !is RectangleMapObject)
                    continue

                val props = obj.getProperties()

                if (props.containsKey("name") && props.get("name") == propertyName)
                    list.add(obj)
            }
        }
        if (list.isEmpty()) println("getRectangle is empty")

        return list

    }

    /**
     * Search the map layers for Tile Objects (tile-like elements of object layers)
     * that contain a property (key) called "name" with associated value propertyName.
     * Typically used to store actor information and will be used to create instances.
     */
    fun getTileList(propertyName: String): ArrayList<MapObject> {
        val list = ArrayList<MapObject>()

        for (layer in tiledMap.layers) {
            for (obj in layer.objects) {
                if (obj !is TiledMapTileMapObject)
                    continue

                val props = obj.getProperties()

                // Default MapProperties are stored within associated Tile object
                // Instance-specific overrides are stored in MapObject

                val t = obj.tile
                val defaultProps = t.properties

                if (defaultProps.containsKey("name") && defaultProps.get("name") == propertyName)
                    list.add(obj)

                // get list of default property keys
                val propertyKeys = defaultProps.keys

                // iterate over keys; copy default values into props if needed
                while (propertyKeys.hasNext()) {
                    val key = propertyKeys.next()

                    // check if value already exists; if not, create property with default value
                    if (props.containsKey(key))
                        continue
                    else {
                        val value = defaultProps.get(key)
                        props.put(key, value)
                    }
                }
            }
        }
        return list
    }


    override fun draw(batch: Batch, parentAlpha: Float) {
        // adjust tilemap camera to stay in sync with main camera
        val mainCamera = stage.camera
        tiledCamera.position.x = mainCamera.position.x
        tiledCamera.position.y = mainCamera.position.y
        tiledCamera.update()
        tiledMapRenderer.setView(tiledCamera)

        // need the following code to force batch order,
        //  otherwise it is batched and rendered last
        batch.end()
        tiledMapRenderer.render()
        batch.begin()
    }

    companion object {
        // window dimensions
        private const val windowWidth = 800
        private const val windowHeight = 600
    }
}
