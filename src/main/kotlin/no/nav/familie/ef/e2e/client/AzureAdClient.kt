package no.nav.familie.ef.e2e.client

import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.content.TextContent
import io.ktor.http.ContentType
import io.ktor.http.formUrlEncode
import no.nav.familie.ef.e2e.config.Configuration
import no.nav.familie.ef.e2e.domene.Token

class AzureAdClient(private val configuration: Configuration.AzureAd) {

    suspend fun hentToken(): Token {
        val formUrlEncode = listOf(
            "client_id" to configuration.clientId,
            "scope" to "api://${configuration.iverksettClientId}/.default",
            "client_secret" to configuration.clientSecret,
            "grant_type" to "client_credentials"
        ).formUrlEncode()

        return httpClient.post {
            url(configuration.tokenEndpoint)
            body = TextContent(formUrlEncode, ContentType.Application.FormUrlEncoded)
        }
    }
}
