package screen.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import tab.home.HomeTab
import tab.profile.ProfileTab
import tab.settings.SettingsTab


data class DetailsScreen (val number: Int) : Screen {
    @Composable
    override fun Content() {
        var lastname by remember { mutableStateOf("") }
        var errorMessage by remember { mutableStateOf("") }
        var successMessage by remember { mutableStateOf("") }
        val navigator = LocalNavigator.current
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("SalesForce Check") },

               // BACK BUTTON ON TOP BAR

//                        navigationIcon = {
//                            IconButton(onClick = { navigator?.pop() }) {
//                                Icon(
//                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                                    contentDescription = "Back Arrow Icon"
//                                )
//                            }
//                        }
                    )

                }
            )
            {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                )
                {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(16.dp))
                    {
                    TextField(
                        value = lastname,
                        onValueChange = { lastname = it },
                        label = { Text("Last Name") },
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                    )

                    Button(
                        onClick = {
                            if (lastname == "Ishfaq") {
                                successMessage = "Welcome Monkey Boy!"
                                errorMessage = ""
                            } else {
                                errorMessage = "No users were found"
                                successMessage = ""
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    {
                        Text("Search")
                    }

                    if (errorMessage.isNotEmpty()) {
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colors.error,
                            modifier = Modifier.padding(top = 8.dp)
                            )
                        }

                        if (successMessage.isNotEmpty()) {
                            Text(
                                text = successMessage,
                                color = MaterialTheme.colors.primary,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }

                        Button(
                            onClick = { navigator?.pop() },
                            modifier = Modifier.fillMaxWidth()
                        )
                        {
                            Text ("Log Out")
                        }

                    }
                }
            }
        }
    }