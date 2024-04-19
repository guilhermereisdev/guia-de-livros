package com.guilhermereisapps.guiadelivros.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.guilhermereisapps.guiadelivros.components.AppBar
import com.guilhermereisapps.guiadelivros.components.InputField

@Preview
@Composable
fun SearchScreen(navController: NavController = rememberNavController()) {
    Scaffold(topBar = {
        AppBar(
            title = "Search Screen",
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            navController = navController,
        ) {
            navController.popBackStack()
        }
    }) { topBarPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(topBarPadding)
                .consumeWindowInsets(topBarPadding)
        ) {
            Column {
                SearchForm(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun SearchForm(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    hint: String = "Search",
    onSearch: (String) -> Unit = {}
) {
    val searchQueryState = rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid: Boolean = remember(searchQueryState.value) {
        searchQueryState.value.trim().isNotEmpty()
    }

    Column {
        InputField(
            valueState = searchQueryState,
            labelId = "Search",
            enabled = true,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onSearch(searchQueryState.value.trim())
                searchQueryState.value = ""
                keyboardController?.hide()
            }
        )
    }
}
