// DetailsScreen.kt
package screen.details

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import kotlinx.coroutines.*
import querySalesforceAccounts
import screen.home.Globals
import screen.home.Globals.accessToken
//import screen.home.HomeScreen.SalesforceAuthenticator.accessToken


data class DetailsScreen(val number: Int) : Screen {
    @Composable
    override fun Content() {
        var lastName by remember { mutableStateOf("") }
        var errorMessage by remember { mutableStateOf("") }
        var successMessage by remember { mutableStateOf("") }
        var billingAddress by remember { mutableStateOf("") }
        val navigator = LocalNavigator.current

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("SalesForce Check") },
                    navigationIcon = {
                        IconButton(onClick = { navigator?.pop() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back Arrow Icon"
                            )
                        }
                    }
                )
            }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (billingAddress.isEmpty()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        TextField(
                            value = lastName,
                            onValueChange = { lastName = it },
                            label = { Text("Last Name") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                        )

                        Button(
                            onClick = {
                                GlobalScope.launch(Dispatchers.Main) {
                                    val accounts = querySalesforceAccounts(lastName, Globals.accessToken, Globals.instanceUrl)
                                    println("hahahah $accessToken")
                                    if (accounts.isEmpty()) {
                                        errorMessage = "No users were found"
                                        successMessage = ""
                                    } else {
                                        val account = accounts[0]
                                        billingAddress = listOfNotNull(
                                            account.billingStreet,
                                            account.billingCity,
                                            account.billingState,
                                            account.billingPostalCode,
                                            account.billingCountry
                                        ).joinToString(", ")
                                        successMessage = "User found!"
                                        errorMessage = ""
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
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
                        ) {
                            Text("Log Out")
                        }
                    }
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("Billing Address: $billingAddress", modifier = Modifier.padding(bottom = 16.dp))

                        Button(
                            onClick = {
                                billingAddress = ""
                                lastName = ""
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Search Again")
                        }
                    }
                }
            }
        }
    }
}
