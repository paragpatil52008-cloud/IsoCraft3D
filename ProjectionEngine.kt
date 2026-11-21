package com.isocraft3d

import kotlin.math.sqrt

/** Lightweight orthographic/isometric geometry helpers. Units are arbitrary drawing units. */
object ProjectionEngine {
    data class Point2(val x: Float, val y: Float)
    data class Point3(val x: Float, val y: Float, val z: Float)
    data class Box3(val width: Float, val depth: Float, val height: Float)

    fun isometric(p: Point3, scale: Float, origin: Point2): Point2 {
        val cos30 = 0.8660254f
        val sin30 = 0.5f
        return Point2(
            origin.x + (p.x - p.y) * cos30 * scale,
            origin.y + ((p.x + p.y) * sin30 - p.z) * scale
        )
    }

    fun front(p: Point3, scale: Float, origin: Point2) =
        Point2(origin.x + p.x * scale, origin.y - p.z * scale)

    fun top(p: Point3, scale: Float, origin: Point2) =
        Point2(origin.x + p.x * scale, origin.y + p.y * scale)

    fun side(p: Point3, scale: Float, origin: Point2) =
        Point2(origin.x + p.y * scale, origin.y - p.z * scale)

    fun boxVertices(box: Box3): List<Point3> = listOf(
        Point3(0f,0f,0f), Point3(box.width,0f,0f),
        Point3(box.width,box.depth,0f), Point3(0f,box.depth,0f),
        Point3(0f,0f,box.height), Point3(box.width,0f,box.height),
        Point3(box.width,box.depth,box.height), Point3(0f,box.depth,box.height)
    )

    // Twelve cuboid edges, indexed into boxVertices().
    val edges = listOf(
        0 to 1, 1 to 2, 2 to 3, 3 to 0,
        4 to 5, 5 to 6, 6 to 7, 7 to 4,
        0 to 4, 1 to 5, 2 to 6, 3 to 7
    )

    fun diagonalLength(box: Box3): Float =
        sqrt(box.width*box.width + box.depth*box.depth + box.height*box.height)
}
