package com.xslt.manager.ui.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.xslt.manager.db.model.TyreInfoModel
import com.xslt.manager.ui.theme.backGround1
import com.xslt.manager.ui.theme.btColor
import com.xslt.manager.util.InputEditText
import com.xslt.manager.util.hdp
import com.xslt.manager.util.wdp

@Composable
fun SellScreen(
    tyreInfoModel: TyreInfoModel,
    onConfirmClick: (Int, String) -> Unit,
    onCancelClick: () -> Unit
) {
    val realOutPrice = remember {
        mutableStateOf(tyreInfoModel.outPrice.toString())
    }
    val sellCount = remember {
        mutableStateOf(0)
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        val (bk) = createRefs()
        Surface(
            Modifier
                .width(800.wdp)
                .height(200.hdp)
                .clip(RoundedCornerShape(10.dp))
                .background(backGround1)
                .constrainAs(bk) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backGround1)
                    .padding(20.dp)
            ) {
                val list = Array(tyreInfoModel.count) {
                    (it + 1).toString()
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "卖出数量:")
                    Spacer(modifier = Modifier.width(10.wdp))
                    SpinnerWidget(modifier = Modifier
                        .width(300.wdp)
                        .height(40.hdp), list = list, onItemSelected = {
                        sellCount.value = it.toInt()
                    })
                }

                Spacer(modifier = Modifier.height(20.hdp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.hdp), verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "实际卖价:")
                    Spacer(modifier = Modifier.width(10.wdp))
                    InputEditText(
                        value = realOutPrice.value.toString(),
                        modifier = Modifier
                            .width(300.wdp)
                            .height(80.hdp)
                            .background(Color.White),
                        onValueChange = {
                            realOutPrice.value = it
                        },
                        contentTextStyle = TextStyle.Default,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        placeHolderString = "输入卖价",
                        hintTextStyle = TextStyle(color = Color.Gray)
                    )
                }

                Spacer(modifier = Modifier.height(20.hdp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.hdp), verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = {
                        onConfirmClick.invoke(sellCount.value, realOutPrice.value)
                    }, colors = ButtonDefaults.buttonColors(containerColor = btColor)) {
                        Text(text = "确认")
                    }
                    Spacer(modifier = Modifier.width(250.wdp))
                    Button(onClick = {
                        onCancelClick.invoke()
                    }, colors = ButtonDefaults.buttonColors(containerColor = btColor)) {
                        Text(text = "取消")
                    }
                }
            }
        }

    }
}