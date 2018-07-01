package com.app.jetpack.data.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import com.google.gson.annotations.SerializedName

@Entity(
        indices = [(Index("id"))],
        primaryKeys = ["id"]
)
data class Repos(
        @field:SerializedName("id")
        val id: Int,
        @field:SerializedName("name")
        val name: String,
        @field:SerializedName("full_name")
        val fullName: String,
        @field:SerializedName("description")
        val description: String?,
        @field:SerializedName("owner")
        @field:Embedded(prefix = "owner_")
        val owner: Owner,
        @field:SerializedName("stargazers_count")
        val stars: Int
) {

    data class Owner(
            @field:SerializedName("id")
            val id: Int,
            @field:SerializedName("login")
            val login: String,
            @field:SerializedName("url")
            val url: String?
    )
}