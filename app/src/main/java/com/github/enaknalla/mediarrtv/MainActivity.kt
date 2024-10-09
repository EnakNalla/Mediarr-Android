package com.github.enaknalla.mediarrtv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Icon
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.ModalNavigationDrawer
import androidx.tv.material3.NavigationDrawerItem
import androidx.tv.material3.NavigationDrawerItemDefaults
import androidx.tv.material3.Surface
import androidx.tv.material3.SurfaceDefaults
import androidx.tv.material3.Text
import com.github.enaknalla.mediarrtv.ui.Screens
import com.github.enaknalla.mediarrtv.ui.screens.home.HomeScreen
import com.github.enaknalla.mediarrtv.ui.theme.DraculaPurple
import com.github.enaknalla.mediarrtv.ui.theme.MediarrTheme
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.CogSolid
import compose.icons.lineawesomeicons.FilmSolid
import compose.icons.lineawesomeicons.HomeSolid
import compose.icons.lineawesomeicons.SearchSolid
import compose.icons.lineawesomeicons.TvSolid
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalTvMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            MediarrTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    colors = SurfaceDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.background
                    ),
                ) {
                    NavHost(navController = navController, startDestination = Screens.Home.title) {
                        composable(Screens.Home.title) {
                            NavigationDrawer(navController) {
                                HomeScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NavigationDrawer(navController: NavController, content: @Composable () -> Unit) {
    val navId = navController.currentBackStackEntry?.destination?.route
    Timber.i("navId: $navId")

    val closeDrawerWidth = 80.dp

    ModalNavigationDrawer(
        drawerContent = {
            Column(
                Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .drawBehind {
                        // Draw a border on the right side of the drawer
                        drawRect(
                            color = DraculaPurple,
                            topLeft = Offset(size.width - 2.dp.toPx(), 0f),
                            size = Size(2.dp.toPx(), size.height)
                        )

                    }
                    .fillMaxHeight()
                    .padding(12.dp)
                    .selectableGroup(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                NavigationDrawerItem(
                    selected = navId == Screens.Search.title,
                    onClick = {
                        navController.navigate(Screens.Search.title)
                    },
                    leadingContent = {
                        Icon(
                            imageVector = LineAwesomeIcons.SearchSolid,
                            contentDescription = null,
                        )
                    }
                ) {
                    Text("Search")
                }

                Column {
                    NavigationDrawerItem(
                        selected = navId == Screens.Home.title,
                        onClick = {
                            navController.navigate(Screens.Home.title)
                        },
                        leadingContent = {
                            Icon(
                                imageVector = LineAwesomeIcons.HomeSolid,
                                contentDescription = null,
                            )
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContentColor = MaterialTheme.colorScheme.primary,
                        )
                    ) {
                        Text("Home")
                    }

                    NavigationDrawerItem(
                        selected = navId == Screens.Movies.title,
                        onClick = {
                            navController.navigate(Screens.Movies.title)
                        },
                        leadingContent = {
                            Icon(
                                imageVector = LineAwesomeIcons.FilmSolid,
                                contentDescription = null,
                            )
                        }
                    ) {
                        Text("Movies")
                    }

                    NavigationDrawerItem(
                        selected = navId == Screens.Tv.title,
                        onClick = {
                            navController.navigate(Screens.Tv.title)
                        },
                        leadingContent = {
                            Icon(
                                imageVector = LineAwesomeIcons.TvSolid,
                                contentDescription = null,
                            )
                        }
                    ) {
                        Text("TV")
                    }
                }

                NavigationDrawerItem(
                    selected = navId == Screens.Settings.title,
                    onClick = {
                        navController.navigate(Screens.Settings.title)
                    },
                    leadingContent = {
                        Icon(
                            imageVector = LineAwesomeIcons.CogSolid,
                            contentDescription = null,
                        )
                    }
                ) {
                    Text("Settings")
                }
            }
        },
        scrimBrush = Brush.horizontalGradient(
            listOf(
                MaterialTheme.colorScheme.surface,
                MaterialTheme.colorScheme.background
            )
        )
    ) {
        Box(
            modifier = Modifier
                .padding(start = closeDrawerWidth)
                .fillMaxSize()
        ) {
            content()
        }
    }
}


