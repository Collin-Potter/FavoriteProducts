package com.example.favoriteproducts.ui.add

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.favoriteproducts.FavoriteProductsApp
import com.example.favoriteproducts.data.model.Product

class AddProductViewModel(application: Application) : AndroidViewModel(application) {

    private val productsRepository = getApplication<FavoriteProductsApp>().getProductsRepostory()

    fun addProduct(product: Product) {
        productsRepository.insertProduct(product)
    }

}