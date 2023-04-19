package com.example.easyflight.feature_flight.presentation


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.easyflight.feature_flight.presentation.flights.MainApp
import com.example.easyflight.ui.theme.EasyFlightTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EasyFlightTheme {
                // A surface container using the 'background' color from the theme
                MainApp()
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    EasyFlightTheme {
//        Surface(
//            modifier = Modifier.fillMaxSize(),
//            color = White
//        ) {
//            SearchContainer(roundTrip = true)
//        }
//
//    }
//}