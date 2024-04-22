package com.guilhermereisapps.guiadelivros.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.guilhermereisapps.guiadelivros.components.AppBar
import com.guilhermereisapps.guiadelivros.components.InputField
import com.guilhermereisapps.guiadelivros.model.Book

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
                BookList(navController)
            }
        }
    }
}

@Composable
fun BookList(navController: NavController) {
    val listOfBooks = listOf<Book>(
        Book("01", "Harry Potter e a Pedra Filosofal", "Aquela lá", "Menino bruxo"),
        Book("02", "Senhor dos Anéis", "Tolkien", "Esconde o anel"),
        Book("03", "O Código da Vinci", "Dan Brown", "Enigmas"),
        Book("04", "Harry Potter e o Prisioneiro de Azkaban", "Aquela lá", "Menino bruxo"),
        Book("05", "Harry Potter e o Enigma do Príncipe", "Aquela lá", "Menino bruxo"),
        Book("06", "Harry Potter e as Relíquias da Morte", "Aquela lá", "Menino bruxo"),
    )
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        items(items = listOfBooks) { book ->
            BookRow(book = book, navController = navController)
        }
    }
}

@Preview
@Composable
fun BookRow(
    book: Book =
        Book("01", "Harry Potter e a Pedra Filosofal", "Aquela lá", "Menino bruxo"),
    navController: NavController = rememberNavController(),
) {
    Card(
        modifier = Modifier
            .clickable { }
            .fillMaxWidth()
            .height(150.dp)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            val imageUrl =
                "http://books.google.com/books/content?id=aGMfCgAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
            Image(
                painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
//                    .aspectRatio(1f)
                    .width(100.dp)
//                    .clip(
//                        shape = RoundedCornerShape(
//                            topStart = 8.dp,
//                            bottomStart = 8.dp,
//                        )
//                    ),
                        ,
                contentScale = ContentScale.FillHeight // Preencher verticalmente sem distorção horizontal
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                Text(text = book.title.toString(), overflow = TextOverflow.Ellipsis)
                Text(
                    text = "Author(s): " + book.authors.toString(), overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.bodySmall
                )
                // TODO: adicionar mais campos aqui
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
