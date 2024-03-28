package com.guilhermereisapps.guiadelivros.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.guilhermereisapps.guiadelivros.screens.ReaderSplashScreen
import com.guilhermereisapps.guiadelivros.screens.details.ReaderDetailsScreen
import com.guilhermereisapps.guiadelivros.screens.home.ReaderHomeScreen
import com.guilhermereisapps.guiadelivros.screens.login.ReaderLoginScreen
import com.guilhermereisapps.guiadelivros.screens.search.ReaderSearchScreen
import com.guilhermereisapps.guiadelivros.screens.stats.ReaderStatsScreen
import com.guilhermereisapps.guiadelivros.screens.update.ReaderUpdateScreen

@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ReaderScreens.SplashScreen.name
    ) {
        composable(ReaderScreens.SplashScreen.name) {
            ReaderSplashScreen(navController)
        }
        composable(ReaderScreens.HomeScreen.name) {
            ReaderHomeScreen(navController)
        }
        composable(ReaderScreens.LoginScreen.name) {
            ReaderLoginScreen(navController)
        }
//        composable(ReaderScreens.ReaderCreateAccountScreen.name) {
//            ReaderCreateAccount(navController)
//        }
        composable(ReaderScreens.SearchScreen.name) {
            ReaderSearchScreen(navController)
        }
        composable(ReaderScreens.DetailsScreen.name) {
            ReaderDetailsScreen(navController)
        }
        composable(ReaderScreens.UpdateScreen.name) {
            ReaderUpdateScreen(navController)
        }
        composable(ReaderScreens.StatsScreen.name) {
            ReaderStatsScreen(navController)
        }
    }
}
