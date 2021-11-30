package no.nav.familie.ef.e2e
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking
import no.nav.familie.ef.e2e.client.AzureAdClient
import no.nav.familie.ef.e2e.client.IverksettClient
import no.nav.familie.ef.e2e.config.Configuration
import no.nav.familie.ef.e2e.domene.IverksettRequest
import org.assertj.core.api.Assertions.assertThat
import java.io.File
import java.util.*

class IverksettTest {
    private val configuration = Configuration()
    private val azureAdClient = AzureAdClient(configuration.azureAd)
    private val iverksettClient = IverksettClient(configuration.iverksettBaseUrl, azureAdClient)

    fun runTests() {
        `kall mot iverksett returnerer status 200`()
    }

    private fun `kall mot iverksett returnerer status 200`() {
        val testIverksettDto = this::class.java.classLoader.getResource("IverksettDtoEksempel.json")!!.readText()
        val dtoMedNyUUID = testIverksettDto.replace("uuidReplace", UUID.randomUUID().toString())
        val newFile = File("src/resources/IverksettDtoEksempelChanged.json")
        newFile.writeText(dtoMedNyUUID)
        val pdf = this::class.java.classLoader.getResource("Test.pdf")!!.file

        val iverksettResponse = runBlocking {
            iverksettClient.iverksett(
                IverksettRequest(newFile, File(pdf))
            )
        }
        iverksettResponse?.status?.value shouldBe 400
        assertThat(iverksettResponse?.status?.value).isEqualTo(400)
        throw Exception("Naisjob skal feile")
    }

}