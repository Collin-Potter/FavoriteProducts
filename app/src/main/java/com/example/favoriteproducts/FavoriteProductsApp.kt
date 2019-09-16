package com.example.favoriteproducts

import android.app.Application
import com.example.favoriteproducts.data.ProductsRepository

class FavoriteProductsApp : Application() {

    fun getProductsRepostory() = ProductsRepository(this)
}