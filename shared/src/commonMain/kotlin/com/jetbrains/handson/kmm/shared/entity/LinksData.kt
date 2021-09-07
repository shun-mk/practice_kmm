package com.jetbrains.handson.kmm.shared.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LinksData(
     @SerialName("mission_patch")
     val missionPatchUrl: String?,
     @SerialName("article_link")
     val articleUrl: String?
)
