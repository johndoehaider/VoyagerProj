// SalesforceApi.kt
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import screen.home.Globals

@Serializable
data class Account(
    val Id: String,
    val Name: String,
    @SerialName("BillingStreet") val billingStreet: String?,
    @SerialName("BillingCity") val billingCity: String?,
    @SerialName("BillingState") val billingState: String?,
    @SerialName("BillingPostalCode") val billingPostalCode: String?,
    @SerialName("BillingCountry") val billingCountry: String?
)

suspend fun querySalesforceAccounts(lastName: String, accessToken: String, baseUrl: String): List<Account> {
    val client = HttpClient(CIO)
    val query = "SELECT+Id,+Name,+BillingStreet,+BillingCity,+BillingState,+BillingPostalCode,+BillingCountry+FROM+Account+WHERE+Name+LIKE+%27%25$lastName%25%27"
    val requestUrl = "$baseUrl/services/data/v61.0/query?q=$query"

    val response: HttpResponse = client.get(requestUrl) {
        headers {
            append(HttpHeaders.Authorization, "Bearer $accessToken")
            append(HttpHeaders.Accept, "application/json")
        }
    }
    val responseBody = response.bodyAsText()
    client.close()

    return try {
        val json = Json { ignoreUnknownKeys = true }
        val jsonObject = json.parseToJsonElement(responseBody).jsonObject
        val records = jsonObject["records"]?.jsonArray ?: return emptyList()
        records.map { json.decodeFromJsonElement<Account>(it) }
    } catch (e: Exception) {
        println("Failed to parse JSON: ${e.message}")
        emptyList()
    }
}
