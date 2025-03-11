package com.example.mentalguru.data.model

import com.google.gson.annotations.SerializedName

data class Quote(
    @SerializedName("_id")
    val id: String,
    val content: String,
    val author: String,
    val tags: List<String>,
    val authorSlug: String,
    val length: Int,
    val dateAdded: String,
    val dateModified: String
)
