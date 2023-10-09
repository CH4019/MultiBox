package com.ch4019.multibox.viewmodel.translation

import kotlinx.serialization.Serializable


@Serializable
data class TranslationResponse(
    val code: Int,
    val data: String,
    val msg: String
)