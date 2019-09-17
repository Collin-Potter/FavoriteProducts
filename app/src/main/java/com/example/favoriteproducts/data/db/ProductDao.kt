package com.example.favoriteproducts.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.favoriteproducts.data.model.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM Product ORDER BY id DESC")
    fun getAll(): LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: Product)

    @Query("DELETE FROM Product")
    fun deleteAll()

    @Delete
    fun delete(product: Product)

    @Query("SELECT * FROM Product WHERE id = :id")
    fun find(id: Int): LiveData<Product>

    @Query("SELECT * FROM Product WHERE name LIKE '%' || :name || '%'")
    fun findBy(name: String): LiveData<List<Product>>
}