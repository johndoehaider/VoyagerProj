package tab.profile

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
import screen.details.DetailsScreen


object ProfileTab : Tab
{
    @Composable
    override fun Content() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        )
        {
            Text("Profile Tab")
        }
    }

    override val options: TabOptions
        @Composable
        get() = remember{
            TabOptions(
                index = 1u,
                title = "Profile",
                icon = null
            )
        }
}