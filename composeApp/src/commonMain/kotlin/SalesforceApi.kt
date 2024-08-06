// SalesforceApi.kt
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.*
import screen.home.Globals
import screen.home.Globals.id

suspend fun querySalesforceAccounts(lastName: String, accessToken: String, baseUrl: String) {
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

    println("Response Body: $responseBody")

    try {
        val json = Json { ignoreUnknownKeys = true }
        val jsonObject = json.parseToJsonElement(responseBody).jsonObject
        val records = jsonObject["records"]?.jsonArray
        if (records != null && records.isNotEmpty()) {
            val firstRecord = records[0].jsonObject
            val name = firstRecord["Id"]?.jsonPrimitive?.content
            val billingStreet = firstRecord["Id"]?.jsonPrimitive?.content
            val billingCity = firstRecord["Id"]?.jsonPrimitive?.content
            val billingCountry = firstRecord["Id"]?.jsonPrimitive?.content
            val billingState = firstRecord["Id"]?.jsonPrimitive?.content
            val billingPostalCode = firstRecord["Id"]?.jsonPrimitive?.content
            val id = firstRecord["Id"]?.jsonPrimitive?.content

            Globals.id = id.toString()
            Globals.name = name.toString()
            Globals.billingStreet = billingStreet.toString()
            Globals.billingCity = billingCity.toString()
            Globals.billingCountry = billingCountry.toString()
            Globals.billingState = billingState.toString()
            Globals.billingPostalCode = billingPostalCode.toString()

            println("First Record ID: $id")
        } else {
            println("No records found")
        }
    } catch (e: Exception) {
        println("Failed to parse JSON: ${e.message}")
    }
}
