package com.example.favoriteproducts.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.favoriteproducts.data.db.ProductDao
import com.example.favoriteproducts.data.db.ProductDatabase
import com.example.favoriteproducts.data.model.Product

class ProductsRepository(application: Application) {

    private val productDao: ProductDao

    init {
        val productDatabase = ProductDatabase.getInstance(application)
        productDao = productDatabase.productDao()
    }

    fun getAllProducts(): LiveData<List<Product>> {
        return productDao.getAll()
    }

    fun insertProduct(product: Product) {
        productDao.insert(product)
    }

    fun findProduct(id: Int): LiveData<Product> {
        return productDao.find(id)
    }

    fun findProduct(name: String): LiveData<List<Product>> {
        return productDao.findBy(name)
    }

    fun removeProduct(product: Product) {

        return productDao.delete(product)
    }
}