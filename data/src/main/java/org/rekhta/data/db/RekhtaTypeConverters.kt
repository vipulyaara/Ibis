package org.rekhta.data.db

import kotlinx.serialization.json.Json

/**
 * Type converters to be used for database entities.
 *
 * TODO: Fix type converters
 */

class RekhtaTypeConverters {
    val json: Json by lazy {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }
}
