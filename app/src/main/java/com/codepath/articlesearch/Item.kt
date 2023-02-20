package com.codepath.articlesearch
import android.content.ClipData.Item
import android.support.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/*"response" nested JSON
     ├─ "docs"  // list of JSON objects
* */

@Keep
@Serializable
data class SearchNewsResponse(
    @SerialName("results")
    val results : List<Items>
)


@Keep
@Serializable
data class Items(
    @SerialName("name")
    val title : String,
    @SerialName("overview")
    val desc : String,
    @SerialName("poster_path")
    val poster_url : String
): java.io.Serializable
