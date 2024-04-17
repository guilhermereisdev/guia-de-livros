package com.guilhermereisapps.guiadelivros.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.guilhermereisapps.guiadelivros.components.MenuItem
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
                )
            },
            floatingActionButton = {
                FABContent {}
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String,
    showProfile: Boolean = true,
    navController: NavController,
) {
    val showMenu = remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (showProfile) {
                    Icon(
                        imageVector = Icons.Default.Book,
                        contentDescription = "Profile icon",
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .scale(1.2f),
                    )
                }
                Text(
                    text = title,
                    modifier = Modifier.padding(start = 12.dp),
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Yellow),
        actions = {
            IconButton(onClick = { showMenu.value = !showMenu.value }) {
                Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "Menu")
            }
            DropdownMenu(expanded = showMenu.value, onDismissRequest = { showMenu.value = false }) {
                MenuItem(text = "Minha conta", showMenu = showMenu)
                MenuItem(text = "Estatísticas", showMenu = showMenu) {
                    navController.navigate(ReaderScreens.StatsScreen.name)
                }
                MenuItem(text = "Sair", showMenu = showMenu) {
                    FirebaseAuth.getInstance().signOut().run {
                        navController.navigate(ReaderScreens.LoginScreen.name)
                    }
                }
            }
        },
    )
}

@Composable
fun HomeContent(navController: NavController) {
    Column(verticalArrangement = Arrangement.Top) {
        Row(modifier = Modifier.align(Alignment.Start)) {
            TitleSection(label = "Sua atividade de leitura no momento:")
        }
        ListCard()
    }
}

@Composable
fun TitleSection(modifier: Modifier = Modifier, label: String) {
    Text(text = label)
}

@Composable
fun ReadingRightNowArea(books: List<Book>, navController: NavController) {

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
        colors = CardDefaults.cardColors(contentColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .padding(4.dp)
            .height(200.dp)
            .width(300.dp)
            .clickable { onPressDetails.invoke(book.title.toString()) }
    ) {
        Column(
            modifier = Modifier.width(screenWidth.dp - (spacing * 2)),
//            horizontalAlignment = Alignment.Start,
        ) {
            Row(horizontalArrangement = Arrangement.Center) {
                Image(
                    painter = rememberAsyncImagePainter("http://books.google.com/books/content?id=aGMfCgAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"),
                    contentDescription = "Book Cover",
                    modifier = Modifier
                        .height(140.dp)
                        .width(100.dp)
                        .padding(4.dp)
                )
                Spacer(modifier = Modifier.width(50.dp))
                Column(
                    modifier = Modifier.padding(top = 25.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        imageVector = Icons.Rounded.FavoriteBorder,
                        contentDescription = "Fav icon",
                        modifier = Modifier.padding(1.dp)
                    )
                    BookRating(score = 3.5)
                }
            }
            Text(
                text = book.title.toString(),
                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = book.authors.toString(),
                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            )
        }
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.fillMaxWidth(),
        ) {
            RoundedButton(label = "Reading", radius = 0)
        }
    }
}

@Composable
fun BookRating(score: Double = 4.5) {
    Surface(
        modifier = Modifier
            .height(70.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(50.dp),
        tonalElevation = 6.dp,
        color = Color.White,
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = Icons.Filled.StarBorder, contentDescription = "Star",
                modifier = Modifier.padding(3.dp)
            )
            Text(text = score.toString(), style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Preview
@Composable
fun RoundedButton(
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
