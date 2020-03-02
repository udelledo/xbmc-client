package org.udelledo.xbmc.client.v8

open class V8Request(val method: String, val params: Any? = null, val id: Long = 1) {
    val jsonrpc = "2.0"
}

open class XbmcResponse<T>(val jsonrpc: String, val id: Long, val result: T)
class ApplicationProperties(val properties: List<String> = listOf("volume", "muted", "name", "version"))
class GetMovieRequest(val properties: List<String>? = null)
class GetMovieResponse(val limits: Limit, val movies: List<Movie>)
class Limit(val start: Long?, val end: Long?, val total: Long?)
class Movie(val movieid: String?, val label: String?)