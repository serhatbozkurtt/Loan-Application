package com.example.loanapplication.retro

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.*
import java.lang.reflect.Type
import java.net.URL
import java.util.*
import java.util.regex.Pattern
import javax.net.ssl.HttpsURLConnection
import kotlin.collections.HashMap


@RequiresApi(api = Build.VERSION_CODES.O)
object getParameter {
    private val pat = Pattern.compile(".*\"access_token\"\\s*:\\s*\"([^\"]+)\".*")
    private const val clientId = "l7xx8455db0e84f840dd89b41fa3731cd159" //clientId
    private const val clientSecret = "18ec41c42850414d8e52b8fdac7da72c" //client secret
    private const val tokenUrl = "https://apigw.vakifbank.com.tr:8443/auth/oauth/v2/token"
    private const val auth = clientId + ":" + clientSecret
    private val authentication = Base64.getEncoder().encodeToString(auth.toByteArray())

    private val getParameters: String
        private get() {
            val content = "grant_type=client_credentials&scope=oob"
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

    fun mapTheThings(keyWord: String, certainValue: String): Map<String, String>? {
        val theThings: MutableMap<String, String> = HashMap()
        //do things to get the Map built
        theThings[keyWord] = certainValue //or something similar
        return theThings
    }


    /*fun make_req (param : String) {

        val map = mutableMapOf(0 to "Geeks")
        map.clear()




        //getParameter()

        //return map
    }*/



     fun getParameter(param : String): Map<Int, String>?{

        var token = ""
        //var reader: BufferedReader? = null

        token = getParameters
         var param =  param

        var content = "{\n" +
                "    \"GroupCode\":\"$param\",\n" +
                "    \"MainGroupCode\":\"\"\n" +
                "} "

        val client = OkHttpClient().newBuilder()
            .build()
        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()

        val body: RequestBody = content.toRequestBody(mediaType)
        val request: Request = Request.Builder()
            .url("https://apigw.vakifbank.com.tr:8443/getParameter")
            .method("POST", body)
            .addHeader("Authorization", "Bearer $token")
            .addHeader("Content-Type", "application/json")
            .build()
        val gson = GsonBuilder().setPrettyPrinting().create()
        val responseBody = client.newCall(request).execute().body
        val entity: info_egitim = gson.fromJson(responseBody!!.string(), info_egitim::class.java)

        //println("entity -->  " + entity)
        var entity_data = gson.toJson(entity.Data)
        //println("entity data pretty -->  " + entity_data)

        val dataegitim: Data_egitim = gson.fromJson(entity_data, Data_egitim::class.java)

        //println("Description --> " + dataegitim.ParameterInfo.get(1))
         println("Description --> " + dataegitim.ParameterInfo)


        var ent1 = dataegitim.ParameterInfo.get(1)
        var etn2 = dataegitim.ParameterInfo.get(2)
        val ent3 = gson.toJson(ent1.Description)
        println("ent get 1 descp --> " + ent1.Description)
        println("ent get 12 descp --> " + etn2.Description)

        var i = 0
        val map = mutableMapOf(0 to "Geeks")
        map.clear()

        val map2 = HashMap<Int, String>()


        while (true) {
            val str : ParameterInfo?
            //str = dataegitim.ParameterInfo.get(i)
            //println("$i null or empty -- $str -- " + str.toString().isEmpty())
            //            if(str.Code.toString().isEmpty()) {
            if(i==dataegitim.ParameterInfo.size) {
                break
            }

            val etc = dataegitim.ParameterInfo.get(i)

            print("Code " + etc.Code)
            println(" Description " + etc.Description)

            var code  = Integer.parseInt(etc.Code)

            var descp = etc.Description
            descp = descp.toString()

            map.put(code, descp)
            println("map -- > " + map.get(2))

            i += 1


        }



        //val prettyJsonString = gson.toJson(entity)
        //println("parameters " + prettyJsonString)


        /*for(var i in jsonData){
            var key = i;
            var val = jsonData[i];
            for(var j in val){
                var sub_key = j;
                var sub_val = val[j];
                console.log(sub_key);
            }
        }
        println("parameter 1 --> " + )*/

        //val categories: List<ParameterInfo>

        return map
    }

    @JvmStatic
    fun main(args: Array<String>) {

        var a = "MSTMusteriSektor"

        getParameter(a)



    }
}

fun <K, V> MutableMap<K, V>.put(key: V?, value: String?) {

}


private fun <T> Gson.fromJson(response2: Response, typeToken: Type?) {

}


private fun HttpsURLConnection?.setRequestProperty(s: String) {

}
