package com.example.harganow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import com.example.harganow.presentation.navigation.MyApp
import com.example.harganow.ui.theme.VerifyComposeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var flag = true
        setContent {
            VerifyComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@Preview
@Composable
fun TestLayout() {
    var name = "Android"


    fun onValueChange(value: String): Unit {
        name = value
    }

    Column(
        content = {
            TextField(value = name, onValueChange = ::onValueChange)
            Row(
                content = {
                    Text(text = "Hello")
                    Text(name)
                }
            )
        }
    )
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    VerifyComposeTheme {
        Greeting("Android")
    }
}