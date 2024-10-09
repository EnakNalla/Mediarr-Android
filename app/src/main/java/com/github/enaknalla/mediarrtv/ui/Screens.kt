package com.github.enaknalla.mediarrtv.ui

sealed class Screens(val title: String) {
    data object Login : Screens("Login")
    data object Home : Screens("home")
    data object Tv : Screens("tv")
    data object Movies : Screens("movies")
    data object Settings : Screens("settings")
    data object Search : Screens("search")
    data object Detail : Screens("detail")
}