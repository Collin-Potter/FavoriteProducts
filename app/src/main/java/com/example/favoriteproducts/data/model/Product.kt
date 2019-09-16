package com.example.favoriteproducts.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    var name: String = "",

    @PrimaryKey(autoGenerate = true) var id: Int = 0
)