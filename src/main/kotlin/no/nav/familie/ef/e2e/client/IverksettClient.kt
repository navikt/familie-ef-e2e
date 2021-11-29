package no.nav.familie.ef.e2e.client

import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.utils.io.core.*
import mu.KotlinLogging
import no.nav.familie.ef.e2e.domene.IverksettRequest
import java.util.*

class IverksettClient(
    private val baseUrl: String,
    private val azureAdClient: AzureAdClient
) {
    private val logger = KotlinLogging.logger { }

    suspend fun iverksett(iverksettRequest: IverksettRequest): HttpResponse? {
        val token = azureAdClient.hentToken()
        logger.info("token length: ${token.token.length}")
        val randomUUID = UUID.randomUUID()
        logger.info("Kaller iverksetting med Nav-Call-Id: $randomUUID fra e2e")

        return httpClient.post {
            url("$baseUrl/api/iverksett")
            header(HttpHeaders.Authorization, "Bearer ${token.token}")
            header("Nav-Call-Id", randomUUID)
            body = MultiPartFormDataContent(formData {
                this.appendInput(
                    key = "fil",
                    headers = Headers.build {
                        append(HttpHeaders.ContentDisposition,"filename=Test.pdf")
                        append(HttpHeaders.ContentType,"application/pdf")
                    },
                    size = iverksettRequest.fil.length()
                ) { buildPacket { writeFully(iverksettRequest.fil.readBytes()) } }
                this.appendInput(
                    key = "data",
                    headers = Headers.build {
                        append(
                            HttpHeaders.ContentDisposition,
                            "filename=IverksettDtoEksempel.json"
                        )
                        append(HttpHeaders.ContentType,"application/json")
                    },
                    size = iverksettRequest.data.length()
                ) { buildPacket { writeFully(iverksettRequest.data.readBytes()) } }
            })
        }
    }
}