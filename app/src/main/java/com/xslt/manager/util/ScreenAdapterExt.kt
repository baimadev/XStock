package com.xslt.manager.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.*
import com.xslt.manager.util.ScreenConfigInfo.heightFactor
import com.xslt.manager.util.ScreenConfigInfo.scale
import com.xslt.manager.util.ScreenConfigInfo.widthFactor

object ScreenConfigInfo {
    //设计图宽高px
    private const val designWidthDp = 1280f
    private const val designHeightDp = 720f

    //高度比例
    var heightFactor = 0f

    //屏幕密度
    var scale = 0f

    //宽度比例
    var  widthFactor = 0f


    @Composable
    fun initScreenConfigInfo() {
        val config = LocalConfiguration.current

        val widthDp = config.screenWidthDp.toFloat()
        val heightDp = config.screenHeightDp.toFloat()

        scale = config.densityDpi/160f

        if (heightFactor == 0f) heightFactor = heightDp / designHeightDp
        if (widthFactor == 0f) widthFactor = widthDp / designWidthDp

    }
}

fun Int.dp2px(): Int {
    return (this * scale + 0.5f).toInt()
}
fun Dp.dp2px(): Int {
    return (this.value * scale + 0.5f).toInt()
}


@Stable
inline val Int.wdp: Dp
    get() {
        val result = this.toFloat() * widthFactor
        return Dp(value = result)
    }

@Stable
inline val Float.wdp: Dp
    get() {
        val result = this.toFloat() * widthFactor
        return Dp(value = result)
    }


@Stable
inline val Int.hdp: Dp
    get() {
        val result = this.toFloat() * heightFactor
        return Dp(value = result)
    }

@Stable
inline val Float.hdp: Dp
    get() {
        val result = this.toFloat() * heightFactor
        return Dp(value = result)
    }

@Stable
inline val Int.spi:TextUnit
    get() {
        return this* heightFactor.sp
}


