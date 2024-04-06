package com.guilhermereisapps.guiadelivros.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.guilhermereisapps.guiadelivros.screens.splash.SplashScreen
import com.guilhermereisapps.guiadelivros.screens.details.DetailsScreen
import com.guilhermereisapps.guiadelivros.screens.home.HomeScreen
import com.guilhermereisapps.guiadelivros.screens.login.LoginScreen
import com.guilhermereisapps.guiadelivros.screens.search.SearchScreen
import com.guilhermereisapps.guiadelivros.screens.stats.StatsScreen
import com.guilhermereisapps.guiadelivros.screens.update.UpdateScreen

@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ReaderScreens.SplashScreen.name
    ) {
        composable(ReaderScreens.SplashScreen.name) {
            SplashScreen(navController)
        }
        composable(ReaderScreens.HomeScreen.name) {
            HomeScreen(navController)
        }
        composable(ReaderScreens.LoginScreen.name) {
            LoginScreen(navController)
        }
//        composable(ReaderScreens.ReaderCreateAccountScreen.name) {
//            ReaderCreateAccount(navController)
//        }
        composable(ReaderScreens.SearchScreen.name) {
            SearchScreen(navController)
        }
        composable(ReaderScreens.DetailsScreen.name) {
            DetailsScreen(navController)
        }
        composable(ReaderScreens.UpdateScreen.name) {
            UpdateScreen(navController)
        }
        composable(ReaderScreens.StatsScreen.name) {
            StatsScreen(navController)
        }
    }
}
