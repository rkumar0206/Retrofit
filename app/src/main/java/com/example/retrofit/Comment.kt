package com.example.retrofit

import com.google.gson.annotations.SerializedName

data class Comment(
    var postId: Int,
    var id: Int,
    var name: String,
    val email: String,
    @SerializedName("body") var text: String
) {
}