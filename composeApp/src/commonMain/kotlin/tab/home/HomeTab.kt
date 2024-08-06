package tab.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import cafe.adriel.voyager.core.screen.Screen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import screen.details.DetailsScreen
import screen.home.HomeScreen


object HomeTab : Tab
{
    @Composable
    override fun Content() {

//THIS ALLOWS HOME.TAB TO CONTROL SCREENS
//        Navigator(HomeScreen()) {navigator ->
//            SlideTransition(navigator)
//        }
//
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        )
        {
            Text("Home Tab")
        }
    }

    override val options: TabOptions
        @Composable
        get() = remember{
            TabOptions(
                index = 0u,
                title = "Home",
                icon = null
            )
        }
}