package com.example.verifycompose.network

import android.app.AlertDialog
import android.content.Context
import android.os.AsyncTask
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder

abstract class BackgroundWorker internal constructor(var context: Context) {
    var alertDialog: AlertDialog? = null
    protected fun doInBackground(vararg params: String): String? {
        val login_url = params[0]
        val type = params[1]
        val name = params[2]
        val username = params[3]
        val password = params[4]
        try {
            val url = URL(login_url)
            val httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.requestMethod = "POST"
            httpURLConnection.doOutput = true
            httpURLConnection.doInput = true
            val outputStream = httpURLConnection.outputStream
            val bufferedWriter = BufferedWriter(OutputStreamWriter(outputStream, "UTF-8"))
            var post_data = ""
            if (type == "register") {
                post_data = (URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&"
                        + URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8"))
            } else if (type == "login") {
                post_data = (URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8"))
            }
            bufferedWriter.write(post_data)
            bufferedWriter.flush()
            bufferedWriter.close()
            outputStream.close()
            val inputStream = httpURLConnection.inputStream
            val bufferedReader = BufferedReader(InputStreamReader(inputStream, "iso-8859-1"))
            var result: String? = ""
            var line: String? = ""
            while (bufferedReader.readLine().also { line = it } != null) {
                result += line
            }
            bufferedReader.close()
            inputStream.close()
            httpURLConnection.disconnect()
            return result
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    fun onPreExecute() {
        alertDialog = AlertDialog.Builder(context).create()
//        alertDialog.setTitle("Login Status")
    }

    fun onPostExecute(result: String?) {
        alertDialog!!.setMessage(result)
        alertDialog!!.show()
        //Toast.makeText(context.getApplicationContext(),result,Toast.LENGTH_LONG).show();
    }
}