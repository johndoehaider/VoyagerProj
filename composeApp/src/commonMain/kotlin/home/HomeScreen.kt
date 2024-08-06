package screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import screen.details.DetailsScreen
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.*
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.IO
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlin.math.log
import kotlin.properties.Delegates

@Serializable
object Globals {
    var accessToken = "boob"
    var instanceUrl = "boob"
}

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var errorMessage by remember { mutableStateOf("") }
        var isLoading by remember { mutableStateOf(false) }

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
        {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp)
            )
            {

                TextField(
                    value = username, onValueChange = { username = it },
                    label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                )

                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                )
                Button(
                    onClick =
                    {
                        isLoading = true
                        authenticateSalesforce(username, password)
                        { success, error ->
                            isLoading = false
                            if (success) {
                                navigator?.push(DetailsScreen(10))
                            } else {
                                errorMessage = "Invalid username or password"
                                println("test output")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                {
                    Text("Login")
                }

                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
                }

                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colors.error,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }

//    object SalesforceAuthenticator {
//        var accessToken: String? by Delegates.observable(null) { _, _, newValue ->
//            println("Access token updated: $newValue")
//        }
//    }

    private fun authenticateSalesforce(
        username: String,
        password: String,
        callback: (Boolean, String?) -> Unit
    ) {
        val client = HttpClient()
        val clientId = "3MVG91oqviqJKoEEwA8i6_zdRgWcFRRHWGBQAip3lJCZpFYuUP_aHpW_MqdSnlEJv9CwanrzwIjc6qQnyIHsx"
        val clientSecret = "9C058A33BB30F7D24917069F64C67C0A6763709607B950F1CCB021BE02EEB25E"

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response: HttpResponse = withContext(Dispatchers.IO) {
                    client.submitForm(
                        url = "https://login.salesforce.com/services/oauth2/token",
                        formParameters = Parameters.build {
                            append("grant_type", "password")
                            append("client_id", clientId)
                            append("client_secret", clientSecret)
                            append("username", username)
                            append("password", password)
                        }
                    )
                }

                val responseBody = response.bodyAsText()
                println("test output2")
                println("response body: $responseBody")

                val success = parseAccessToken(responseBody) != null
                callback(success, if (success) null else "Invalid credentials")
                println("test output3")
            } catch (e: Exception) {
                println(e.message)
                e.printStackTrace()
                callback(false, e.message)
            } finally {
                client.close()

            }
        }
    }

    private fun parseAccessToken(responseBody: String): String? {

        //make responseBody store the acess token locally, so that this call can work
        val jsonObject = Json.parseToJsonElement(responseBody).jsonObject
        Globals.accessToken = jsonObject["access_token"]?.jsonPrimitive?.contentOrNull.toString()
        Globals.instanceUrl = jsonObject["instance_url"]?.jsonPrimitive?.contentOrNull.toString()
        println("test output4: ${Globals.accessToken}")
        return jsonObject["access_token"]?.jsonPrimitive?.contentOrNull
    }
}
