package com.guilhermereisapps.guiadelivros.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.guilhermereisapps.guiadelivros.components.AppBar
import com.guilhermereisapps.guiadelivros.model.Book
import com.guilhermereisapps.guiadelivros.navigation.ReaderScreens
import com.guilhermereisapps.guiadelivros.ui.theme.GuiaDeLivrosTheme

@Preview
@Composable
fun HomeScreen(navController: NavController = rememberNavController()) {
    GuiaDeLivrosTheme {
        Scaffold(
            topBar = {
                AppBar(
                    title = "Guia de Livros",
                    navController = navController,
                    showProfile = true,
                )
            },
            floatingActionButton = {
                FABContent {
                    navController.navigate(ReaderScreens.SearchScreen.name)
                }
            }
        ) { topBarPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(topBarPadding)
                    .consumeWindowInsets(topBarPadding)
            ) {
                HomeContent(navController)
            }
        }
    }
}

@Composable
fun HomeContent(navController: NavController) {
    val listOfBooks = listOf<Book>(
        Book("01", "Harry Potter e a Pedra Filosofal", "Aquela lá", "Menino bruxo"),
        Book("02", "Senhor dos Anéis", "Tolkien", "Esconde o anel"),
        Book("03", "O Código da Vinci", "Dan Brown", "Enigmas"),
        Book("04", "Harry Potter e o Prisioneiro de Azkaban", "Aquela lá", "Menino bruxo"),
        Book("05", "Harry Potter e o Enigma do Príncipe", "Aquela lá", "Menino bruxo"),
        Book("06", "Harry Potter e as Relíquias da Morte", "Aquela lá", "Menino bruxo"),
    )

    Column(
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start)
        ) {
            TitleSection(label = "Livros sendo lidos no momento:")
        }
        ReadingRightNowArea(books = listOf(), navController = navController)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start)
        ) {
            TitleSection(label = "Minha biblioteca:")
        }
        BookListArea(listOfBooks = listOfBooks, navController = navController)
    }
}

@Composable
fun BookListArea(listOfBooks: List<Book>, navController: NavController) {
    HorizontalScrollableComponent(listOfBooks) {
        //TODO: ao clicar no card, irá para a tela de detalhes do livro
    }
}

@Composable
fun HorizontalScrollableComponent(listOfBooks: List<Book>, onCardPressed: (String) -> Unit) {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .horizontalScroll(scrollState)
    ) {
        for (book in listOfBooks) {
            ListCard(book) {
                onCardPressed(it)
            }
        }
    }
}

@Composable
fun TitleSection(modifier: Modifier = Modifier, label: String) {
    Text(text = label)
}

@Composable
fun ReadingRightNowArea(books: List<Book>, navController: NavController) {
    ListCard()
}

@Preview
@Composable
fun ListCard(
    book: Book = Book("01", "O Código DaVinci", "Dan Brown", "The Best Book"),
    onPressDetails: (String) -> Unit = {},
) {
    val context = LocalContext.current
    val resources = context.resources
    val displayMetrics = resources.displayMetrics
    val screenWidth = displayMetrics.widthPixels / displayMetrics.density
    val spacing = 10.dp

    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .padding(4.dp)
            .height(150.dp)
            .width(300.dp)
            .clickable { onPressDetails.invoke(book.title.toString()) }
    ) {
        Row(
//            modifier = Modifier.width(screenWidth.dp - (spacing * 2)),
//            horizontalAlignment = Alignment.Start,
        ) {
            Image(
                painter = rememberAsyncImagePainter("http://books.google.com/books/content?id=aGMfCgAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"),
                contentDescription = "Book Cover",
                modifier = Modifier
                    .height(150.dp)
                    .width(100.dp),
                colorFilter = ColorFilter.tint(Color.Black)
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxHeight()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.End,
                ) {
                    Icon(
                        imageVector = Icons.Rounded.FavoriteBorder,
                        contentDescription = "Fav icon",
                        modifier = Modifier.padding(1.dp)
                    )
                    BookRating(score = 3.5)
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = book.title.toString(),
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = book.authors.toString(),
                    )
                }
            }


        }
    }
}

@Composable
fun BookRating(score: Double = 4.5) {
    Surface(
        modifier = Modifier
            .padding(4.dp),
        shape = RoundedCornerShape(50.dp),
        tonalElevation = 6.dp,
        color = Color.White,
    ) {
        Row(
            modifier = Modifier.padding(start = 4.dp, top = 4.dp, bottom = 4.dp, end = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Filled.StarBorder, contentDescription = "Star",
                modifier = Modifier.padding(3.dp)
            )
            Text(text = score.toString(), style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
fun StatusLabel(
    label: String = "Reading",
    radius: Int = 29,
    onPress: () -> Unit = {}
) {
    Surface(
        modifier = Modifier.clip(
            RoundedCornerShape(
                bottomEndPercent = radius,
                topStartPercent = radius,
            )
        ),
        color = Color.Yellow
    ) {
        Column(
            modifier = Modifier
                .width(90.dp)
                .heightIn(40.dp)
                .clickable { onPress.invoke() },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = label, color = Color.Black)
        }
    }
}

@Composable
fun FABContent(onTap: () -> Unit) {
    FloatingActionButton(
        onClick = { onTap() },
        shape = RoundedCornerShape(50.dp),
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add a book")
    }
}
