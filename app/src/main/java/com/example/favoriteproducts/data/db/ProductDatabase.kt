package com.example.favoriteproducts.data.db

import android.app.Application
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.favoriteproducts.data.model.Product
import com.example.favoriteproducts.data.net.ProductInfoProvider


@Database(entities = [Product::class], version = 1)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {
        private val lock = Any()
        private const val DB_NAME = "Product.db"
        private var INSTANCE: ProductDatabase? = null


        fun getInstance(application: Application): ProductDatabase {
            synchronized(ProductDatabase.lock) {
                if (ProductDatabase.INSTANCE == null) {
                    ProductDatabase.INSTANCE =
                        Room.databaseBuilder(application, ProductDatabase::class.java, ProductDatabase.DB_NAME)
                            .allowMainThreadQueries()
                            .addCallback(object : RoomDatabase.Callback() {
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    super.onCreate(db)
                                    ProductDatabase.INSTANCE?.let {
                                        ProductDatabase.prePopulate(it, ProductInfoProvider.productList)
                                    }
                                }
                            })
                            .build()
                }
                return ProductDatabase.INSTANCE!!
            }
        }

        fun prePopulate(database: ProductDatabase, productList: List<Product>) {
            for (product in productList) {
                AsyncTask.execute { database.productDao().insert(product) }
            }
        }
    }
}