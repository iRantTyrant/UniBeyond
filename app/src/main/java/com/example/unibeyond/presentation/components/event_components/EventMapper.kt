package com.example.unibeyond.presentation.components.event_components

import com.example.unibeyond.domain.model.Event
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.maplibre.spatialk.geojson.Feature
import org.maplibre.spatialk.geojson.FeatureCollection
import org.maplibre.spatialk.geojson.Geometry
import org.maplibre.spatialk.geojson.Point
import org.maplibre.spatialk.geojson.Position

fun List<Event>.toFeatureCollection() : FeatureCollection<Geometry, JsonObject> {
    val features = this.map { event ->
        val position = Position(longitude = event.longitude, latitude = event.latitude)
        val geometry = Point(position)
        val jsonProperties = buildJsonObject {
            put("eventId",event.eventId)
            put("eventName", event.eventName)
        }
        Feature(geometry = geometry, properties = jsonProperties)
    }

    return FeatureCollection<Geometry, JsonObject>(features = features)
}