package com.xslt.manager.ui.home

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.xslt.manager.ui.home.screen.*
import com.xslt.manager.ui.theme.XStockTheme

class MainActivity : ComponentActivity() {
    lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN) //隐藏状态栏
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        homeViewModel.initData(this)

        homeViewModel.toastEvent.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        setContent {
            XStockTheme {
                MyAppNavHost(modifier = Modifier.fillMaxSize(), viewModel = homeViewModel)
            }
        }
    }
}

@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "home",
    viewModel: HomeViewModel
) {

    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = startDestination
    ) {
        composable("home") {
            HomeScreen(
                onStockClick = { navController.navigate("stock") },
                onCheckInClick = { navController.navigate("checkIn") },
                onOrderClick = { navController.navigate("order") },
            )

        }
        composable("stock") {
            StockManageScreen(viewModel){
                navController.navigate("home")
            }
            //popUpTo("home")
            //navController.navigate("friendsList")
        }
        composable("checkIn") {
            CheckInScreen(viewModel){
                navController.navigate("home")
            }
        }
        composable("order") {
            OrderManagerScreen(viewModel)
        }
    }
}
