package com.example.favoriteproducts.data.net

import com.example.favoriteproducts.data.model.Product

class ProductInfoProvider {

    companion object {
        var productList = initProductList()

        private fun initProductList(): MutableList<Product> {
            var products = mutableListOf<Product>()
            products.add(
                Product("TEST")
            )
            return products
        }
    }
}