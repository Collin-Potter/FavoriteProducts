package com.example.favoriteproducts.ui.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.favoriteproducts.FavoriteProductsApp
import com.example.favoriteproducts.data.model.Product

class ProductListViewModel(application: Application) : AndroidViewModel(application) {

    private val productsRepository = getApplication<FavoriteProductsApp>().getProductsRepostory()
    private val productList = MediatorLiveData<List<Product>>()

    init {
        getAllProducts()
    }

    fun getProductList(): LiveData<List<Product>> {
        return productList
    }

    fun getAllProducts() {
        productList.addSource(productsRepository.getAllProducts()) { products ->
            productList.postValue(products)
        }
    }

    fun searchProducts(name: String) {
        productList.addSource(productsRepository.findProduct(name)) { products ->
            productList.postValue(products)
        }
    }

    fun removeProduct(product: Product) {
        productsRepository.removeProduct(product)
    }
}