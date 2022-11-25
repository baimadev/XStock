package com.xslt.manager.ui.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.xslt.manager.db.model.OrderModel
import com.xslt.manager.ui.home.HomeViewModel
import com.xslt.manager.ui.theme.backGround1
import com.xslt.manager.ui.theme.backGround2
import com.xslt.manager.util.formatTime
import com.xslt.manager.util.hdp
import com.xslt.manager.util.wdp

@Composable
fun OrderManagerScreen(viewModel: HomeViewModel) {

    val orderInfoList by viewModel.orderListInfo
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backGround1)
    ) {
        val (profile, list) = createRefs()
        Column(
            Modifier
                .fillMaxSize()
                .constrainAs(list) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }) {
            GridHeader(modifier = Modifier, "品牌", "型号", "售卖日期", "进价", "卖价", "数量", "盈利")
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(orderInfoList.size) { index ->
                    val orderModel = orderInfoList[index]
                    GridHeader(
                        modifier = Modifier,
                        str1 = orderModel.brand,
                        str2 = orderModel.type,
                        str3 = formatTime(orderModel.sellTime),
                        str4 = orderModel.inPrice.toString(),
                        str5 = orderModel.outPrice.toString(),
                        str6 = orderModel.count.toString(),
                        str7 = orderModel.getProfit().toString()
                    )
                }

            }
        }

        Box(modifier = Modifier
            .constrainAs(profile) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom,100.hdp)
            }
            .clip(RoundedCornerShape(10.dp))
            .width(500.wdp)
            .height(50.hdp)
            .background(backGround2),
            contentAlignment = Alignment.Center
        ) {
            Text(text = viewModel.getAllProfile(), style = TextStyle(fontSize = 20.sp))
        }
    }


}

@Composable
fun GridHeader(
    modifier: Modifier,
    str1: String,
    str2: String,
    str3: String,
    str4: String,
    str5: String,
    str6: String,
    str7: String
) {

    Row(modifier = modifier.fillMaxWidth()) {
        OutLineText(
            modifier = Modifier
                .width(150.wdp)
                .height(40.dp), str1
        )
        OutLineText(
            modifier = Modifier
                .width(280.wdp)
                .height(40.dp), str2
        )
        //售卖日期
        OutLineText(
            modifier = Modifier
                .width(300.wdp)
                .height(40.dp), str3
        )
        OutLineText(
            modifier = Modifier
                .width(150.wdp)
                .height(40.dp), str4
        )
        OutLineText(
            modifier = Modifier
                .width(150.wdp)
                .height(40.dp), str5
        )
        //数量
        OutLineText(
            modifier = Modifier
                .width(100.wdp)
                .height(40.dp), str6
        )
        OutLineText(
            modifier = Modifier
                .width(150.wdp)
                .height(40.dp), str7
        )
    }

}

@Composable
fun OutLineText(modifier: Modifier, text: String) {
    Box(
        modifier = modifier

            .border(1.dp, color = Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Text(text)
    }
}