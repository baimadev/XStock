package com.xslt.manager.db


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.xslt.manager.db.dao.OrderDao
import com.xslt.manager.db.dao.TyreInfoDao
import com.xslt.manager.db.model.OrderModel
import com.xslt.manager.db.model.TyreInfoModel


/**
 * room数据库
 * */

@Database(entities = [
    TyreInfoModel::class,
    OrderModel::class
], version = 1,exportSchema = false)

abstract class AppDatabase : RoomDatabase() {

 abstract fun  tyreInfoDao() :  TyreInfoDao
 abstract fun  orderDao() :  OrderDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "xslt_store.db")
                .addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                        }
                    }
                )
                .build()
        }
    }
}

