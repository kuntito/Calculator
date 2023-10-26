package com.example.calc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.calc.ui.composeables.Calculator
import com.example.calc.ui.composeables.CalcSplashScreen
import com.example.calc.ui.theme.colorPrimaryLighter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = viewModel<CalcViewModel>()
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = colorPrimaryLighter
            ) {
                Navigation(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun Navigation(
    viewModel: CalcViewModel
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash_screen") {
        composable("splash_screen") {
            CalcSplashScreen(navController = navController)
        }
        composable("main_screen") {
            Calculator(viewModel = viewModel)
        }
    }
}



