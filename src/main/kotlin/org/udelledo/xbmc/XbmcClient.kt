package org.udelledo.xbmc

import com.fasterxml.jackson.module.kotlin.readValue
import org.udelledo.mapper
import org.udelledo.xbmc.client.v8.*
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class XbmcClient(private val host: String) {
    val targetUrl: String
        get() {
            val url = URL(host)
            return when (host) {
                "${url.protocol}://${url.host}" -> host + DEFAULT_CONTEXT
                "${url.protocol}://${url.host}:${url.port}" -> host + DEFAULT_CONTEXT
                else -> if (host.indexOf(JSONRPC_CONTEXT) > 0) {
                    host
                } else {
                    host + JSONRPC_CONTEXT
                }
            }
        }

    private fun parseResponse(responseStream: InputStream): String {
        BufferedReader(InputStreamReader(responseStream)).use {
            val response = StringBuffer()

            var inputLine = it.readLine()
            while (inputLine != null) {
                response.append(inputLine)
                inputLine = it.readLine()
            }
            it.close()
            return response.toString()
        }
    }

    private fun postRequest(targetUrl: String, request: Any): String {
        with(URL(targetUrl).openConnection() as HttpURLConnection) {
            requestMethod = "POST"
            doOutput = true
            this.setRequestProperty("Content-Type", "application/json; utf-8")
            val wr = OutputStreamWriter(outputStream)
            wr.write(mapper.writeValueAsString(request))
            wr.flush()
            return when (responseCode) {
                200 -> parseResponse(inputStream)
                else -> parseResponse(errorStream)
            }
        }
    }

    fun getApplicationProperties(): XbmcResponse<Any> {
        val response = postRequest(targetUrl, V8Request("Application.GetProperties", ApplicationProperties()))
        return mapper.readValue(response)
    }

    fun getMovies(properties: List<FieldMovies>? = null, limits: Limit? = null, sort: Sort? = null): XbmcResponse<GetMovieResponse> {

        val response = postRequest(targetUrl, V8Request("VideoLibrary.GetMovies", GetMovieRequest(properties, limits, sort)))
        return mapper.readValue(response)
    }

    companion object {
        const val JSONRPC_CONTEXT = "jsonrpc"
        const val DEFAULT_CONTEXT = "/$JSONRPC_CONTEXT"
    }
}

