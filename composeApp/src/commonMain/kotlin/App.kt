import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsProperties.Selected
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import screen.home.HomeScreen
import tab.home.HomeTab
import tab.profile.ProfileTab
import tab.settings.SettingsTab

import voyagerproj.composeapp.generated.resources.Res
import voyagerproj.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    MaterialTheme{

//THIS NAVIGATES BETWEEN TABS
//        TabNavigator(HomeTab){
//            Scaffold(
//                bottomBar = {
//                    BottomNavigation{
//                        TabNavigationItem(HomeTab)
//                        TabNavigationItem(ProfileTab)
//                        TabNavigationItem(SettingsTab)
//                    }
//                }
//            )
//            {
//                CurrentTab()
//            }
//        }

//THIS NAVIGATES BETWEEN SCREENS

         Navigator(HomeScreen())
         {
            navigator ->
            SlideTransition(navigator)
        }
    }
}

//THIS FUNCTION IS USED TO NAVIGATE BETWEEN TABS
//@Composable
//private fun RowScope.TabNavigationItem(tab: Tab)
//{
//    val tabNavigator = LocalTabNavigator.current
//    BottomNavigationItem(
//        selected = tabNavigator.current == tab,
//        onClick = {tabNavigator.current = tab},
//        label = {Text(tab.options.title)},
//        icon = {}
//    )
//
//}