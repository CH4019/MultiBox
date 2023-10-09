package com.ch4019.multibox.viewmodel.oneWord

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OneWordResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("uuid")
    val uuid: String,
    @SerialName("hitokoto")
    val hitokoto: String,
    @SerialName("type")
    val type: String,
    @SerialName("from")
    val from: String,
    @SerialName("from_who")
    val fromWho: String?,
//    @SerialName("creator")
//    val creator: String,
//    @SerialName("creator_uid")
//    val creatorUid: Int,
//    @SerialName("reviewer")
//    val reviewer: Int,
//    @SerialName("commit_from")
//    val commitFrom: String,
//    @SerialName("created_at")
//    val createdAt: String,
//    @SerialName("length")
//    val length: Int
)