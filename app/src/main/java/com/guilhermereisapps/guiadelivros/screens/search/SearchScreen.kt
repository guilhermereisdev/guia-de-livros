package com.guilhermereisapps.guiadelivros.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.guilhermereisapps.guiadelivros.components.AppBar
import com.guilhermereisapps.guiadelivros.components.InputField
import com.guilhermereisapps.guiadelivros.components.SingleBookCard
import com.guilhermereisapps.guiadelivros.model.ReaderBook

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = viewModel(),
) {
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
                        .padding(16.dp),
                    viewModel = viewModel,
                ) { query ->
                    viewModel.searchBooks(query)
                }
                BookList(navController, viewModel)
            }
        }
    }
}

@Composable
fun BookList(navController: NavController, viewModel: SearchViewModel) {
    val listOfBooks = listOf<ReaderBook>(
        ReaderBook("01", "Harry Potter e a Pedra Filosofal", "Aquela lá", "Menino bruxo"),
        ReaderBook("02", "Senhor dos Anéis", "Tolkien", "Esconde o anel"),
        ReaderBook("03", "O Código da Vinci", "Dan Brown", "Enigmas"),
        ReaderBook("04", "Harry Potter e o Prisioneiro de Azkaban", "Aquela lá", "Menino bruxo"),
        ReaderBook("05", "Harry Potter e o Enigma do Príncipe", "Aquela lá", "Menino bruxo"),
        ReaderBook("06", "Harry Potter e as Relíquias da Morte", "Aquela lá", "Menino bruxo"),
    )
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        items(items = listOfBooks) { book ->
            SingleBookCard(book = book, navController = navController)
        }
    }
}

@Composable
fun SearchForm(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel,
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
