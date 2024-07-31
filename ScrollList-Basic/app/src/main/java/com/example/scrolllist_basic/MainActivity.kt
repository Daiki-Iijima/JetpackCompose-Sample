package com.example.scrolllist_basic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.scrolllist_basic.ui.theme.ScrollListBasicTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScrollListBasicTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ScrollList(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ScrollList(modifier: Modifier = Modifier) {

    //  テストデータの生成
    val testData = List(100) { "データ項目 ${it + 1}" }

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        //  1つのデータを表示
        item {
            Text(text = "First Item")
        }

        //  複数のデータを表示
        items(testData) { data ->
            Text(text = data)
        }

        //  1つのデータを表示
        item {
            Text(text = "Last Item")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ScrollListBasicTheme {
        ScrollList()
    }
}