package com.xslt.manager.util

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch


/**
 * @ClassName : KotlinFlowExt
 * @Description:
 * @Author: WuZhuoyu
 * @Date: 2021/6/11 12:16
 */

fun <T> Flow<T>.catchLog(): Flow<T> {
    return this.catch {
        Log.e("Flow Catch","error : ${it.message ?: it.toString()}")
    }
}

