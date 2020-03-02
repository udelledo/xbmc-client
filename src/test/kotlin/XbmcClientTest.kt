import org.junit.jupiter.api.Test
import org.udelledo.xbmc.XbmcClient

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
    fun `Client can get Movies`(){
        val testSubject = XbmcClient(host)
        val response = testSubject.getMovies()
        assert(response.result.movies.isNotEmpty())
    }
}