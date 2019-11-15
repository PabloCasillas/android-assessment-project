package com.vp.list.model

import com.google.gson.annotations.SerializedName

import java.util.Collections.emptyList

data class SearchResponse(
    @SerializedName("Response")
    val response: String,
    @SerializedName("Search")
    val search: List<ListItem>,
    val hasResponse: Boolean = POSITIVE_RESPONSE == response,
    val totalResults: Int = search.size
) {
    companion object {
        private const val POSITIVE_RESPONSE = "True"
    }
}
