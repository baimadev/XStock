package com.xslt.manager.db.dao

import androidx.room.*
import com.xslt.manager.db.model.TyreInfoModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TyreInfoDao {

    @Query("SELECT * FROM TyreInfoModel")
    fun getAll(): Flow<List<TyreInfoModel>>

    @Update
    suspend fun updateTyreInfoModel(tyreInfoModel: TyreInfoModel)

    @Insert
    suspend fun insert(tyreInfoModel: TyreInfoModel)

    @Delete
    suspend fun delete(tyreInfoModel: TyreInfoModel)

}