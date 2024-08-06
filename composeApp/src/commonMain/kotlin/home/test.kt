
import kotlinx.serialization.json.*


private fun parseAccessToken(responseBody: String): String? {

    //make responseBody store the acess token locally, so that this call can work

    val jsonObject = Json.parseToJsonElement(responseBody).jsonObject
    return jsonObject["access_token"]?.jsonPrimitive?.contentOrNull
}