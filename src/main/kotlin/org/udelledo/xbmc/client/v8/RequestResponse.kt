package org.udelledo.xbmc.client.v8

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

open class V8Request(val method: String, val params: Any? = null, val id: Long = 1) {
    val jsonrpc = "2.0"
}

open class XbmcResponse<T>(val jsonrpc: String, val id: Long, val result: T)
class ApplicationProperties(val properties: List<String> = listOf("volume", "muted", "name", "version"))
class GetMovieRequest(val properties: List<FieldMovies>? = null, val limits: Limit? = null, val sort: Sort? = null)
class GetMovieResponse(val limits: Limit, val movies: List<Movie>)
class Limit(val start: Long? = null, val end: Long? = null, val total: Long? = null)
class Movie(val movieid: String?, val label: String?, val art: ArtWork?, val cast: List<CastMember>?, val country: List<String>?,
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss") val dateadded: Date?, val director: List<String>?,
            val genre: List<String>?, val imdbnumber: String?, val lastplayed: Date?, val mpaa: String?, val originaltitle: String?, val playcount: Long?,
            val plot: String?, val plotoutline: String?, val premiered: Date?, val rating: Double?, val ratings: Ratings?, val resume: Resume?,
            val runtime: Long?, val set: String?, val setid: Long?, val showlink: List<String>?, val sorttitle: String?, val streamdetails: StreamDetail?,
            val studio: List<String>?, val tag: List<Any>?, val tagline: String?, val title: String?, val top250: Long?, val trailer: String?, val uniqueid: UniqueId?,
            val userrating: Double?, val votes: Long?, val writer: List<String>?, val year: Int?, val fanart: String?, val file: String?, val thumbnail: String?)

class ArtWork(val fanart: String?, val poster: String?,
              @JsonProperty("set.fanart") val setFanart: String?,
              @JsonProperty("set.poster") val setPoster: String?)

class CastMember(val name: String?, val order: Long?, val role: String?, val thumbnail: String?)
class Ratings(val default: Rating?)

class Rating(val default: Boolean?, val rating: Double?, val votes: Long?)
class Resume(val position: Long?, val total: Long?)
class StreamDetail(val audio: List<Audio>?, val subtitle: List<Any>?, val video: List<Video>?)
class Audio(val channels: Long?, val codec: String?, val language: String?)
class Video(val aspect: Double?, val codec: String?, val duration: Long?, val height: Long?, val language: String?, val stereomode: String?, val width: Long?)
class UniqueId(val imdb: String?)
class Sort(val properties: List<SortProperties> = SortProperties.values().asList())

enum class SortProperties {
    ignorearticle, method, order
}

enum class FieldMovies {
    title, genre, year, rating, director, trailer, tagline, plot, plotoutline, originaltitle,
    lastplayed, playcount, writer, studio, mpaa, cast, country, imdbnumber, runtime, set, showlink, streamdetails,
    top250, votes, fanart, thumbnail, file, sorttitle, resume, setid, dateadded, tag, art, userrating, ratings,
    premiered, uniqueid
}