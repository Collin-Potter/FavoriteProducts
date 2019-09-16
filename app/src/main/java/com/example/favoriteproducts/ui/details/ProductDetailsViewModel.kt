package com.example.favoriteproducts.ui.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.favoriteproducts.FavoriteProductsApp
import com.example.favoriteproducts.data.model.Product

class ProductDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val productsRepository = getApplication<FavoriteProductsApp>().getProductsRepostory()
    private val productId= MutableLiveData<Int>()

    fun getProductDetails(id: Int): LiveData<Product> {
        productId.value = id
        val productDetails = Transformations.switchMap<Int, Product>(productId) { id ->
            productsRepository.findProduct(id)
        }
        return productDetails
    }
}