package com.guilhermereisapps.guiadelivros.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Preview
@Composable
fun ReaderSplashScreen(navController: NavController = NavController(LocalContext.current)) {
    Surface(
        modifier = Modifier
            .padding(15.dp)
            .size(330.dp),
        shape = CircleShape,
        color = Color.White,
        border = BorderStroke(width = 2.dp, color = Color.LightGray)
    ) {
        Column(
            modifier = Modifier.padding(1.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Guia de Livros",
                style = MaterialTheme.typography.displayMedium,
                color = Color.Red,
            )
            Text(
                text = "Acompanhe sua leitura",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.LightGray
            )
        }
    }
}
