package com.xslt.manager.ui.home.screen

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.xslt.manager.db.model.TyreInfoModel
import com.xslt.manager.ui.home.HomeViewModel
import com.xslt.manager.ui.theme.backGround1
import com.xslt.manager.ui.theme.backGround2
import com.xslt.manager.ui.theme.bkTransparent
import com.xslt.manager.ui.theme.btColor
import com.xslt.manager.util.InputEditText
import com.xslt.manager.util.hdp
import com.xslt.manager.util.spi
import com.xslt.manager.util.wdp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun StockManageScreen(viewModel: HomeViewModel, onLeftClick: () -> Unit) {
    val context = LocalContext.current
    Surface(modifier = Modifier.fillMaxSize()) {
        val brandList by viewModel.getBrandList()
        val showFirstList = remember { mutableStateOf(true) }
        val showSecondList = remember { mutableStateOf(false) }
        val showTyreInfo = remember { mutableStateOf(false) }
        val showSellScreen = remember { mutableStateOf(false) }

        val tyreInfoList: MutableState<List<TyreInfoModel>> =
            remember { mutableStateOf(emptyList()) }
        val tyreInfoModel: MutableState<TyreInfoModel?> =
            remember { mutableStateOf(null) }

        val countValue = remember { mutableStateOf("") }

//        val onRightClick = {
//            showFirstList.value = true
//            showSecondList.value = false
//            showTyreInfo.value = false
//            showSellScreen.value = false
//        }

        AnimatedVisibility(visible = showFirstList.value) {
            Header(
                modifier = Modifier,
                title = "选择品牌",
                onLeftClick = { onLeftClick.invoke() },
                onRightClick = { onLeftClick.invoke() })
            {
                Spacer(modifier = Modifier.height(20.hdp))

                if (brandList.isEmpty()) {
                    Text(text = "库存为空，请先登记入库。")
                } else {
                    LazyColumn {
                        items(brandList) { brand ->
                            BrandItem(name = brand) {
                                showSecondList.value = true
                                showFirstList.value = false
                                tyreInfoList.value = viewModel.getTyreList(brand)
                            }
                        }
                    }
                }
            }
        }

        AnimatedVisibility(visible = showSecondList.value) {
            Header(modifier = Modifier, title = "选择型号", onLeftClick = {
                showSecondList.value = false
                showFirstList.value = true
            }, onRightClick = { onLeftClick() }) {
                Spacer(modifier = Modifier.height(20.hdp))

                LazyColumn() {
                    items(tyreInfoList.value) { data ->
                        BrandItem(name = data.getType()) {
                            showSecondList.value = false
                            tyreInfoModel.value = data
                            countValue.value = data.count.toString()
                            showTyreInfo.value = true
                        }
                    }
                }
            }
        }

        AnimatedVisibility(visible = showTyreInfo.value) {
            Header(modifier = Modifier, title = tyreInfoModel.value!!.getType(), onLeftClick = {
                showSecondList.value = true
                showTyreInfo.value = false
            }, onRightClick = { onLeftClick() }) {
                Spacer(modifier = Modifier.height(20.hdp))
                TyreInfoWidget(
                    homeViewModel = viewModel,
                    countValue = countValue,
                    tyreInfoModel = tyreInfoModel.value!!,
                    onSaveClick = {
                        viewModel.updateTyreModel(it)
                    },
                    onDeleteClick = {
                        viewModel.deleteTyreModel(it)
                        showFirstList.value = true
                        showSecondList.value = false
                        showTyreInfo.value = false
                        showSellScreen.value = false
                    }
                ) {
                    if (it.count <= 0) {
                        Toast.makeText(context, "库存不足", Toast.LENGTH_SHORT).show()
                    } else {
                        showSellScreen.value = true
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = showSellScreen.value, modifier = Modifier
                .fillMaxSize()
                .background(bkTransparent)
        ) {
            SellScreen(tyreInfoModel = tyreInfoModel.value!!, onConfirmClick = { count, price ->
                viewModel.sellTyre(tyreInfoModel.value!!, count, price)
                showSellScreen.value = false
                countValue.value = ((countValue.value.toInt()) - count).toString()
            }) {
                showSellScreen.value = false
            }
        }
    }
}

@Composable
fun BrandItem(name: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick.invoke()
            }, horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(5.dp))
        Box(
            modifier = Modifier
                .width(1200.wdp)
                .height(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White), contentAlignment = Alignment.Center
        ) {
            Text(
                text = name,
                modifier = Modifier,
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
    }
}

//轮胎详情页
@Composable
fun TyreInfoWidget(
    homeViewModel: HomeViewModel,
    tyreInfoModel: TyreInfoModel,
    countValue: MutableState<String>,
    onSaveClick: (TyreInfoModel) -> Unit,
    onDeleteClick: (TyreInfoModel) -> Unit,
    onSellClick: (TyreInfoModel) -> Unit,
) {
    Box(contentAlignment = Alignment.TopCenter, modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .width(1200.wdp)
                .height(600.hdp)
        ) {
            Row() {
                Text(text = tyreInfoModel.brand, style = TextStyle(fontSize = 20.sp))
                Spacer(modifier = Modifier.width(10.wdp))
                Text(text = tyreInfoModel.getType(), style = TextStyle(fontSize = 20.sp))
            }

            Spacer(modifier = Modifier.height(10.hdp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "数量:", style = TextStyle(fontSize = 20.sp))
                Spacer(modifier = Modifier.width(10.wdp))
                InputEditText(
                    value = countValue.value,
                    modifier = Modifier
                        .width(250.wdp)
                        .height(40.hdp)
                        .background(Color.White),
                    onValueChange = {
                        countValue.value = it
                    },
                    contentTextStyle = TextStyle.Default.copy(fontSize = 20.sp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )
            }

            Spacer(modifier = Modifier.height(10.hdp))
            val inPrices = remember { mutableStateOf(tyreInfoModel.inPrice.toString()) }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "进价:", style = TextStyle(fontSize = 20.sp))
                Spacer(modifier = Modifier.width(10.wdp))

                InputEditText(
                    value = inPrices.value,
                    modifier = Modifier
                        .width(250.wdp)
                        .height(40.hdp)
                        .background(Color.White),
                    onValueChange = {
                        inPrices.value = it
                    },
                    contentTextStyle = TextStyle.Default.copy(fontSize = 20.sp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )
            }

            Spacer(modifier = Modifier.height(10.hdp))
            val outPrices = remember { mutableStateOf(tyreInfoModel.outPrice.toString()) }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "卖价:", style = TextStyle(fontSize = 20.sp))
                Spacer(modifier = Modifier.width(10.wdp))
                InputEditText(
                    value = outPrices.value,
                    modifier = Modifier
                        .width(250.wdp)
                        .height(40.hdp)
                        .background(Color.White),
                    onValueChange = {
                        outPrices.value = it
                    },
                    contentTextStyle = TextStyle.Default.copy(fontSize = 20.sp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )
            }

            Spacer(modifier = Modifier.height(10.hdp))

            Row(horizontalArrangement = Arrangement.Center) {
                Button(onClick = {
                    if (tyreInfoModel.inPrice != inPrices.value.toInt() ||
                        tyreInfoModel.outPrice != outPrices.value.toInt() ||
                        tyreInfoModel.count != countValue.value.toInt()
                    ) {
                        homeViewModel.showToast("请先保存修改！")
                    } else {
                        onSellClick.invoke(tyreInfoModel)
                    }
                }, colors = ButtonDefaults.buttonColors(containerColor = btColor)) {
                    Text(text = "卖出", style = TextStyle(fontSize = 20.sp))
                }
                Spacer(modifier = Modifier.width(150.wdp))

                Button(onClick = {
                    tyreInfoModel.inPrice = inPrices.value.toInt()
                    tyreInfoModel.outPrice = outPrices.value.toInt()
                    tyreInfoModel.count = countValue.value.toInt()
                    onSaveClick.invoke(tyreInfoModel)
                }, colors = ButtonDefaults.buttonColors(containerColor = btColor)) {
                    Text(text = "保存修改", style = TextStyle(fontSize = 20.sp))
                }

                Spacer(modifier = Modifier.width(150.wdp))

                Button(onClick = {
                    onDeleteClick.invoke(tyreInfoModel)
                }, colors = ButtonDefaults.buttonColors(containerColor = btColor)) {
                    Text(text = "删除", style = TextStyle(fontSize = 20.sp))
                }
            }

        }
    }

}

@Composable
fun Header(
    modifier: Modifier,
    title: String,
    leftStr: String = "返回",
    onLeftClick: () -> Unit,
    onRightClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backGround1)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(backGround2),
        ) {
            val (icon, desc, titleFlag, right) = createRefs()

            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(icon) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start, 10.dp)
                    }
                    .clickable { onLeftClick.invoke() })

            Text(text = leftStr, fontSize = 15.spi, modifier = Modifier
                .constrainAs(desc) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(icon.end, 10.dp)
                }
                .clickable { onLeftClick.invoke() })

            Text(
                text = title,
                style = TextStyle(fontSize = 20.spi),
                modifier = Modifier.constrainAs(titleFlag) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })

            if (onRightClick != null) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null,
                    modifier = Modifier
                        .constrainAs(right) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end, 10.dp)
                        }
                        .clickable { onRightClick.invoke() })
            }
        }

        content()
    }


}