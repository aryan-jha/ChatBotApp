package com.example.chatbot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatbot.Domain.ViewModel.chatViewModel
import com.example.chatbot.Presentation.ChatBotScreen
import com.example.chatbot.Presentation.SplashScreen
import com.example.chatbot.ui.theme.ChatBotTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {

    val viewModel : chatViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChatBotTheme {
                Scaffold(modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
//                            .padding(innerPadding)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFFA1C4FD), // Light Blue
                                        Color(0xFFC2E9FB), // Light Purple
                                        Color(0xFFFDD5E5)  // Light Pink
                                    )
                                )
                            )
                    ) {
                        val navController = rememberNavController()

                        NavHost(navController = navController, startDestination = splashscreen) {

                            composable<splashscreen> {
                                SplashScreen(navController)
                            }
                            composable<chatBotScreen> {
                                ChatBotScreen(navController,viewModel = viewModel)
                            }

                        }
                    }
                }
            }
        }
    }
}

@Serializable
object splashscreen

@Serializable
object chatBotScreen