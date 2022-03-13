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
object kredi_req_interface {
    private val pat = Pattern.compile(".*\"access_token\"\\s*:\\s*\"([^\"]+)\".*")
    private const val clientId = "l7xxed288aaf5982491192fced7804f51ea3" //clientId
    private const val clientSecret = "9757e1f2e2114f9cbf84af3c63ec1c7a" //client secret
    private const val tokenUrl = "https://apigw.vakifbank.com.tr:8443/auth/oauth/v2/token"
    private const val scope = "loan"
    private const val auth = clientId + ":" + clientSecret
    private val authentication = Base64.getEncoder().encodeToString(auth.toByteArray())


    private val clientCredentials: String
        private get() {
            val content = "grant_type=client_credentials&scope=loan"
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




    fun getKrediCevap(aa: kredi_istek): Map<Int, String>?{

        var token = ""

        token = clientCredentials

        var kredi_response = mutableMapOf(0 to "kredi_response")
        kredi_response.clear()
        var kredi_response_list = ArrayList<String>()


        println("token --> " + token )

        var content = "{\"IdentityNumber\": \"${aa.IdentityNumber}\" ,    \n" +
                "\t\"PhoneNumber\": \"${aa.PhoneNumber}\",    \n" +
                "\t\"LoanAmount\": ${aa.LoanAmount},    \n" +
                "\t\"LoanTerm\": ${aa.LoanTerm},      \n" +
                "\t\"MonthlyIncome\": ${aa.MonthlyIncome},      \n" +
                "\t\"EmploymentStatus\": ${aa.EmploymentStatus},    \n" +
                "\t\"ResidenceType\": ${aa.ResidenceType},\n" +
                "\t\"Sector\": ${aa.Sector},    \n" +
                "\t\"ProfessionCode\": ${aa.ProfessionCode},    \n" +
                "\t\"EducationLevel\": ${aa.EducationLevel},    \n" +
                "\t\"TitleCode\": ${aa.TitleCode}}"


        val client = OkHttpClient().newBuilder()
                .build()
        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()

        val body: RequestBody = content.toRequestBody(mediaType)
        val request: Request = Request.Builder()
                .url("https://apigw.vakifbank.com.tr:8443/personalLoanApplication")
                .method("POST", body)
                .addHeader("Authorization", "Bearer $token")
                .addHeader("Content-Type", "application/json")
                .build()
        val gson = GsonBuilder().setPrettyPrinting().create()
        val responseBody = client.newCall(request).execute().body
        val entity: loan_response = gson.fromJson(responseBody!!.string(), loan_response::class.java)

        println("entity -->  " + entity)
        var entity_data = gson.toJson(entity.Data)
        println("entity data pretty -->  " + entity_data)

        val data_loan: Data_loan = gson.fromJson(entity_data, Data_loan::class.java)

        println("data loan -> " + data_loan.ApplicationResult)

        val StatusCode  = Integer.parseInt(data_loan.ApplicationResult?.StatusCode)
        val FstatusCode = Integer.parseInt(data_loan.ApplicationResult?.FailedStatusCode)
        var StatusDescription = data_loan.ApplicationResult?.StatusDescription
        var FstatusDescription = data_loan.ApplicationResult?.FailedStatusDescription
        StatusDescription = StatusDescription.toString()
        FstatusDescription = FstatusDescription.toString()


        kredi_response.put(StatusCode, StatusDescription)
        kredi_response.put(FstatusCode, FstatusDescription)

        println("kredi response map -->  " + kredi_response)
        return kredi_response

    }


    @JvmStatic
    fun main(args: Array<String>) {


        val kredi_req = kredi_istek("12345678911", "05847774147", 1000, 12,
                5000, 1, 1, 1, 1, 1, 1)

        getKrediCevap(kredi_req)



    }
}


private fun <T> Gson.fromJson(response2: Response, typeToken: Type?) {

}


private fun HttpsURLConnection?.setRequestProperty(s: String) {

}
