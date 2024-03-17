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
        startDestination = ReaderScreens.ReaderSplashScreen.name
    ) {
        composable(ReaderScreens.ReaderSplashScreen.name) {
            ReaderSplashScreen(navController)
        }
        composable(ReaderScreens.ReaderHomeScreen.name) {
            ReaderHomeScreen(navController)
        }
        composable(ReaderScreens.ReaderLoginScreen.name) {
            ReaderLoginScreen(navController)
        }
//        composable(ReaderScreens.ReaderCreateAccountScreen.name) {
//            ReaderCreateAccount(navController)
//        }
        composable(ReaderScreens.ReaderSearchScreen.name) {
            ReaderSearchScreen(navController)
        }
        composable(ReaderScreens.ReaderDetailsScreen.name) {
            ReaderDetailsScreen(navController)
        }
        composable(ReaderScreens.ReaderUpdateScreen.name) {
            ReaderUpdateScreen(navController)
        }
        composable(ReaderScreens.ReaderStatsScreen.name) {
            ReaderStatsScreen(navController)
        }
    }
}
