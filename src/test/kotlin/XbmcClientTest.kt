import org.junit.jupiter.api.Test
import org.udelledo.xbmc.XbmcClient
import org.udelledo.xbmc.client.v8.*

class XbmcClientTest {

    private val host = System.getenv("XBMC_HOST")!!

    @Test
    fun `Client is initialized`() {
        val testSubject = XbmcClient(host)
        assert(testSubject.targetUrl == host.plus("/jsonrpc"))
    }

    @Test
    fun `Client can read setting`() {
        val testSubject = XbmcClient(host)
        val response = testSubject.getApplicationProperties()
        val result = response.result as Map<String, Any>
        val version = result["version"] as Map<String, Any>
        assert(result["name"] == "Kodi")
        assert(version["major"] == 18)
    }

    @Test
    fun `Client can get Movies with all details`() {
        val testSubject = XbmcClient(host)
        testSubject.getMovies(FieldMovies.values().asList(), Limit(0, 1), Sort(listOf(SortProperties.ignorearticle))
        ).run {
            assert(result.movies.isNotEmpty())
            result.movies[0].let { movie ->
                movie.javaClass.declaredMethods.asSequence().filter { it.name.startsWith("get") }.forEach { getter ->
                    val value = getter.invoke(movie)
                    value != null
                    if (value is ArtWork) {
                        value.javaClass.declaredMethods.asSequence().filter { it.name.startsWith("get") }
                                .forEach { artworkGetter ->
                                    artworkGetter.invoke(value) != null
                                }
                    }
                }
            }
        }
        testSubject.getMovies()
                .run {
                    assert(result.movies.isNotEmpty())
                }
    }
}