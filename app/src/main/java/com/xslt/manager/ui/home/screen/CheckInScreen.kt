package com.xslt.manager.ui.home.screen

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import com.xslt.manager.util.hdp
import com.xslt.manager.util.wdp
import com.xslt.manager.db.model.TyreInfoModel
import com.xslt.manager.ui.home.HomeViewModel
import com.xslt.manager.ui.theme.backGround1
import com.xslt.manager.ui.theme.backGround2
import com.xslt.manager.util.InputEditText
import java.lang.Exception

@Composable
fun CheckInScreen(viewModel: HomeViewModel, onLeftClick: () -> Unit) {

    Header(modifier = Modifier.fillMaxSize(), onLeftClick = {
        onLeftClick.invoke()
    }, leftStr = "返回首页", title = "登记入库") {
        Spacer(modifier = Modifier.height(20.dp))

        ConstraintLayout(modifier = Modifier.fillMaxSize().background(backGround1)) {
            val (title, brand, type, count, outPrice, inPrice, checkIn) = createRefs()
            val inPrices = remember { mutableStateOf("") }
            val outPrices = remember { mutableStateOf("") }
            val brandStr = remember { mutableStateOf("") }
            val widthStr = remember { mutableStateOf("") }
            val flatRatioStr = remember { mutableStateOf("") }
            val hubStr = remember { mutableStateOf("") }
            val countStr = remember { mutableStateOf("") }
            val context = LocalContext.current


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.hdp)
                    .constrainAs(brand) {
                        top.linkTo(title.bottom, 50.hdp)
                        start.linkTo(parent.start, 15.wdp)
                    }, verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "品牌")
                Spacer(modifier = Modifier.width(10.wdp))
                SpinnerWidget(
                    modifier = Modifier.width(500.wdp),
                    list = TyreInfoModel.defaultBrandList,
                    onItemSelected = {
                        brandStr.value = it
                    })
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.hdp)
                    .constrainAs(type) {
                        top.linkTo(brand.bottom, 50.hdp)
                        start.linkTo(parent.start, 15.wdp)
                    }, verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "型号")
                Spacer(modifier = Modifier.width(10.wdp))
                SpinnerWidget(
                    modifier = Modifier.width(280.wdp),
                    list = TyreInfoModel.defaultWidthList,
                    onItemSelected = {
                        widthStr.value = it
                    })
                Spacer(modifier = Modifier.width(10.wdp))
                SpinnerWidget(
                    modifier = Modifier.width(250.wdp),
                    list = TyreInfoModel.defaultFlatRatioList,
                    onItemSelected = {
                        flatRatioStr.value = it
                    })
                Spacer(modifier = Modifier.width(10.wdp))
                SpinnerWidget(
                    modifier = Modifier.width(250.wdp),
                    list = TyreInfoModel.defaultHubList,
                    onItemSelected = {
                        hubStr.value = it
                    })
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.hdp)
                    .constrainAs(count) {
                        top.linkTo(type.bottom, 50.hdp)
                        start.linkTo(parent.start, 15.wdp)
                    }, verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "数量")
                Spacer(modifier = Modifier.width(10.wdp))
                SpinnerWidget(
                    modifier = Modifier.width(250.wdp),
                    list = TyreInfoModel.defaultCountList,
                    onItemSelected = {
                        countStr.value = it
                    })
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.hdp)
                    .constrainAs(inPrice) {
                        top.linkTo(count.bottom, 50.hdp)
                        start.linkTo(parent.start, 15.wdp)
                    }, verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "进价")
                Spacer(modifier = Modifier.width(10.wdp))
                InputEditText(
                    value = inPrices.value.toString(),
                    modifier = Modifier.width(300.wdp).height(100.hdp).background(Color.White),
                    onValueChange = {
                        inPrices.value = it
                    },
                    contentTextStyle = TextStyle.Default,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    placeHolderString = "输入进价",
                    hintTextStyle = TextStyle(color = Color.Gray)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.hdp)
                    .constrainAs(outPrice) {
                        top.linkTo(inPrice.bottom, 50.hdp)
                        start.linkTo(parent.start, 15.wdp)
                    }, verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "卖价")
                Spacer(modifier = Modifier.width(10.wdp))
                InputEditText(
                    value = outPrices.value.toString(),
                    modifier = Modifier.width(300.wdp).height(100.hdp).background(Color.White),
                    onValueChange = {
                        outPrices.value = it
                    },
                    contentTextStyle = TextStyle.Default,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    placeHolderString = "输入卖价",
                    hintTextStyle = TextStyle(color = Color.Gray)
                )
            }

            Button(modifier = Modifier.constrainAs(checkIn) {
                top.linkTo(outPrice.bottom, 50.hdp)
                start.linkTo(parent.start, 15.wdp)
            }, onClick = {
                if (brandStr.value.isEmpty() || countStr.value.isEmpty() || hubStr.value.isEmpty() || widthStr.value.isEmpty() || flatRatioStr.value.isEmpty() || countStr.value.isEmpty()) {
                    Toast.makeText(context, "请输入信息", Toast.LENGTH_SHORT).show()
                } else {
                    val tyreInfoModel = TyreInfoModel(
                        brand = brandStr.value,
                        count = countStr.value.toInt(),
                        hub = hubStr.value,
                        width = widthStr.value,
                        flatRatio = flatRatioStr.value,
                        inPrice = safeToFloat(inPrices.value),
                        outPrice = safeToFloat(outPrices.value)
                    )
                    viewModel.addNewTyreInfo(tyreInfoModel)
                }

            }) {
                Text(text = "确认入库")
            }

        }
    }
}

fun safeToFloat(str: String): Int {
    val result: Int = try {
        str.toInt()
    } catch (e: Exception) {
        0
    }
    return result
}

@Composable
fun SpinnerWidget(
    modifier: Modifier = Modifier,
    list: Array<String>,
    onItemSelected: (String) -> Unit
) {
    AndroidView(
        factory = { context ->
            Spinner(context).apply {
                adapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list)
                setSelection(0)
                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        onItemSelected.invoke(list[position])
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }
                }
            }
        }, modifier = modifier
            .background(Color.White)
            .fillMaxHeight()
    )
}
