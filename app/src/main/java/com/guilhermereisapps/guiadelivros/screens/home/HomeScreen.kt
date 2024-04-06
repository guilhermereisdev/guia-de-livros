package com.guilhermereisapps.guiadelivros.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.guilhermereisapps.guiadelivros.navigation.ReaderScreens
import com.guilhermereisapps.guiadelivros.ui.theme.GuiaDeLivrosTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
        ) {
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {

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
