package com.sdzshn3.demo

import com.google.gson.annotations.SerializedName

data class Student(
    @SerializedName("roll") var roll: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("localtion") var location: String? = null,
    @SerializedName("tag_line") var tagLine: String? = null
)