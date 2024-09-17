package eu.petrfaruzel.scratcher.features.activation.data

import kotlinx.serialization.Serializable

@Serializable
data class VersionDTO(
    val ios: String,
    val android: String
)