package com.example.listusertestapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.listusertestapp.naviagtion.NavGraph
import com.example.listusertestapp.ui.theme.ListUserTestAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


class MainActivity : ComponentActivity() {

    private var keepSplashOn = true
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
       val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { keepSplashOn }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch {
            // Simulate your required loading/branding delay
            delay(2_000L) // Wait for 2 seconds (2000ms)

            // 4. Set the condition to false to dismiss the splash screen
            keepSplashOn = false
        }

        setContent {
            ListUserTestAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize().padding(start = 4.dp, end = 4.dp, top = 16.dp, bottom = 16.dp)) { innerPadding ->
                    ContactList(innerPadding = innerPadding)
                }
            }
        }
    }

}




@Composable
fun ContactList(innerPadding: PaddingValues,
                viewModel: UserViewModel = koinViewModel()) {

    val navHostController: NavHostController = rememberNavController()


    NavGraph(navHostController,viewModel,innerPadding)



}

@Preview
@Composable
fun ContractPreview() {
    //ContactList(Modifier.padding(12.dp))
}
