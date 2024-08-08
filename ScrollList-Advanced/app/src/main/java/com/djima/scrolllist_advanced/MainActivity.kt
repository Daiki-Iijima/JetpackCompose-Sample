package com.djima.scrolllist_advanced

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.djima.scrolllist_advanced.ui.theme.ScrollListAdvancedTheme

data class SelectData(
    val name: String,
    var selected: MutableState<Boolean>,
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScrollListAdvancedTheme {
                ScrollListScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScrollListScreen(modifier: Modifier = Modifier) {
    var enableMultiselectFlag by remember { mutableStateOf(false) }

    val testData = remember {
        mutableStateListOf<SelectData>().apply {
            repeat(100) {
                add(
                    SelectData(
                        "データ項目:$it",
                        mutableStateOf(false)
                    )
                )
            }
        }
    }

    val selectedData = testData.filter { it.selected.value }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(colors = topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ), title = {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(text = "LazyColumnデモ")
                }
            }, actions = {
                Button(
                    onClick = {
                        enableMultiselectFlag = !enableMultiselectFlag
                    }
                ) {
                    Text(text = if (enableMultiselectFlag) "選択解除" else "複数選択")

                    //  選択解除されたらフラグを初期化
                    if (!enableMultiselectFlag) {
                        for (data in selectedData) {
                            data.selected.value = false
                        }
                    }
                }
            }

            )
        },
        floatingActionButton = {
            if (!enableMultiselectFlag) {
                return@Scaffold
            }
            if (selectedData.isEmpty()) {
                return@Scaffold
            }
            FloatingActionButton(
                onClick = {
                    var showText = ""
                    for (data in selectedData) {
                        showText += data.name+","
                    }

                    Toast
                        .makeText(context, showText, Toast.LENGTH_SHORT)
                        .show()
                },
                modifier = Modifier.width(100.dp)
            ) {
                Text(text = "${selectedData.count()}件選択")
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        ScrollView(
            testData = testData,
            enableMultiselect = enableMultiselectFlag,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@SuppressLint("ReturnFromAwaitPointerEventScope")
@Composable
fun ScrollView(
    testData: List<SelectData>,
    enableMultiselect: Boolean,
    modifier: Modifier
) {
    val context = LocalContext.current

    LazyColumn(
        //  各要素の間を10あける
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
    ) {
        items(testData) { data ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .clickable {
                        //  複数選択の場合
                        if (enableMultiselect) {
                            data.selected.value = !data.selected.value
                            return@clickable
                        } else {
                            //  単体選択の場合
                            Toast
                                .makeText(context, "on click ${data.name}", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            ) {
                Text(text = data.name, modifier = Modifier.weight(1f))
                if (enableMultiselect) {
                    Checkbox(
                        checked = data.selected.value,
                        onCheckedChange = { data.selected.value = it })
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ScrollListAdvancedTheme {
        ScrollListScreen()
    }
}