package com.xslt.manager.ui.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import com.xslt.manager.ui.theme.backGround1
import com.xslt.manager.ui.theme.btColor
import com.xslt.manager.util.hdp

@Composable
fun HomeScreen(
    onStockClick: () -> Unit,
    onCheckInClick: () -> Unit,
    onOrderClick: () -> Unit,
) {
    Surface(modifier = Modifier
        .fillMaxSize()
        .background(backGround1)) {
        ConstraintLayout(modifier = Modifier
            .fillMaxSize()
            .background(backGround1)) {
            val (stockBt, checkIn, orderScreen) = createRefs()
            Button(
                modifier = Modifier.constrainAs(stockBt) {
                    top.linkTo(parent.top, 100.hdp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                onClick = { onStockClick.invoke() },
                colors = ButtonDefaults.buttonColors(containerColor = btColor)
            ) {
                Text(text = "库存管理")
            }

            Button(
                modifier = Modifier.constrainAs(checkIn) {
                    top.linkTo(stockBt.bottom, 20.hdp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                onClick = { onCheckInClick.invoke() },
                colors = ButtonDefaults.buttonColors(containerColor = btColor)
            ) {
                Text(text = "登记入库")
            }

            Button(
                modifier = Modifier.constrainAs(orderScreen) {
                    top.linkTo(checkIn.bottom, 20.hdp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                onClick = { onOrderClick.invoke() },
                colors = ButtonDefaults.buttonColors(containerColor = btColor)
            ) {
                Text(text = "订单管理")
            }


        }

    }
}