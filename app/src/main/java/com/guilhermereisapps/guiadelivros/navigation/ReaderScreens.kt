package com.guilhermereisapps.guiadelivros.navigation

enum class ReaderScreens {
    ReaderSplashScreen,
    ReaderLoginScreen,
    ReaderCreateAccountScreen,
    ReaderHomeScreen,
    ReaderSearchScreen,
    ReaderDetailsScreen,
    ReaderUpdateScreen,
    ReaderStatsScreen;

    companion object {
        fun fromRoute(route: String?): ReaderScreens =
            when (route?.substringBefore("/")) {
                ReaderSplashScreen.name -> ReaderSplashScreen
                ReaderLoginScreen.name -> ReaderLoginScreen
                ReaderCreateAccountScreen.name -> ReaderCreateAccountScreen
                ReaderHomeScreen.name -> ReaderHomeScreen
                ReaderSearchScreen.name -> ReaderSearchScreen
                ReaderDetailsScreen.name -> ReaderDetailsScreen
                ReaderUpdateScreen.name -> ReaderUpdateScreen
                ReaderStatsScreen.name -> ReaderStatsScreen
                null -> ReaderHomeScreen
                else -> throw IllegalArgumentException("Rota $route não é reconhecida.")
            }
    }
}
