package com.example.loanapplication.retro

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.*
import java.lang.reflect.Type
import java.net.URL
import java.util.*
import java.util.regex.Pattern
import javax.net.ssl.HttpsURLConnection


@RequiresApi(api = Build.VERSION_CODES.O)
object clientcredential {
    private val pat = Pattern.compile(".*\"access_token\"\\s*:\\s*\"([^\"]+)\".*")
    private const val clientId = "l7xx052c5c1953a141d09d7ceabb221aa757" //clientId
    private const val clientSecret = "f78b4311a28b4a56a901e06383e79634" //client secret
    private const val tokenUrl = "https://apigw.vakifbank.com.tr:8443/auth/oauth/v2/token"
    private const val auth = clientId + ":" + clientSecret
    private val authentication = Base64.getEncoder().encodeToString(auth.toByteArray())

    private val clientCredentials: String
        private get() {
            val content = "grant_type=client_credentials"
            var reader: BufferedReader? = null
            var connection: HttpsURLConnection? = null
            var returnValue = ""
            try {
                val url = URL(tokenUrl)
                connection = url.openConnection() as HttpsURLConnection
                connection.requestMethod = "POST"
                connection!!.doOutput = true
                connection.setRequestProperty("Authorization", "Basic " + authentication)
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
                connection.setRequestProperty("Accept", "application/json")
                val os = PrintStream(connection.outputStream)
                os.print(content)
                os.close()
                reader = BufferedReader(InputStreamReader(connection.inputStream))
                var line: String? = null
                val out =
                    StringWriter(if (connection.contentLength > 0) connection.contentLength else 2048)
                while (reader.readLine().also { line = it } != null) {
                    out.append(line)
                }

                val response = out.toString()
                val matcher = pat.matcher(response)
                if (matcher.matches() && matcher.groupCount() > 0) {
                    returnValue = matcher.group(1)
                }
            } catch (e: Exception) {
                println("Error : " + e.message)
            } finally {
                if (reader != null) {
                    try {
                        reader.close()
                    } catch (e: IOException) {
                    }
                }
                connection!!.disconnect()
            }
            return returnValue
        }






    fun getCurrencyRate(){

        var token = ""
        //var reader: BufferedReader? = null

        token = clientCredentials

        var content = "{\r\n    \"ValidityDate\" : \"2022-01-20T12:05:06+03:00\"\r\n}"

        val client = OkHttpClient().newBuilder()
                .build()
        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()

        val body: RequestBody = content.toRequestBody(mediaType)
        val request: Request = Request.Builder()
                .url("https://apigw.vakifbank.com.tr:8443/getCurrencyRates")
                .method("POST", body)
                .addHeader("Authorization", "Bearer $token")
                .addHeader("Content-Type", "application/json")
                .build()
        val gson = GsonBuilder().setPrettyPrinting().create()
        val responseBody = client.newCall(request).execute().body
        val entity: response_json = gson.fromJson(responseBody!!.string(), response_json::class.java)



        val prettyJsonString = gson.toJson(entity)
        println(prettyJsonString)




    }





    @JvmStatic
    fun main(args: Array<String>) {

        getCurrencyRate()

    }
}


private fun <T> Gson.fromJson(response2: Response, typeToken: Type?) {

}


private fun HttpsURLConnection?.setRequestProperty(s: String) {

}
