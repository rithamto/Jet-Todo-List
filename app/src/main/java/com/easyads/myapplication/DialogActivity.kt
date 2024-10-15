package com.easyads.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class DialogActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dialogText = intent.getStringExtra("DIALOG_TEXT") ?: "Default Text"

        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "dialog") {
                composable("dialog") {
                    DetailDialog(
                        text = dialogText,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun DetailDialog(text: String, navController: NavHostController) {
    AlertDialog(
        onDismissRequest = {
            navController.popBackStack()
        },
        dismissButton = {
            Button(onClick = {
                navController.popBackStack()
            }) {
                Text(text = "cancle")
            }
        },
        confirmButton = {
            Button(onClick = {}) {
                Text(text = "yes")
            }
        },
        text = { Text(text = text) },
    )
}