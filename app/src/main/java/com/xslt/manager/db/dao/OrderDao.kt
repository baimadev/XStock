package com.xslt.manager.db.dao

import androidx.room.*
import com.xslt.manager.db.model.OrderModel
import com.xslt.manager.db.model.TyreInfoModel
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {

    @Query("SELECT * FROM OrderModel")
    fun getAll(): Flow<List<OrderModel>>

    @Update
    suspend fun updateTyreInfoModel(orderModel: OrderModel)

    @Insert
    suspend fun insert(orderModel: OrderModel)

    @Delete
    suspend fun delete(orderModel: OrderModel)

}