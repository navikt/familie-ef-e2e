package no.nav.familie.ef.e2e.config

import com.natpryce.konfig.ConfigurationMap
import com.natpryce.konfig.ConfigurationProperties
import com.natpryce.konfig.EnvironmentVariables
import com.natpryce.konfig.Key
import com.natpryce.konfig.overriding
import com.natpryce.konfig.stringType

private val defaultProperties = ConfigurationMap(
    mapOf(
        "AZURE_APP_CLIENT_ID" to "",
        "AZURE_APP_CLIENT_SECRET" to "",
        "IVERKSETTING_CLIENT_ID" to "",
        "AZURE_OPENID_CONFIG_TOKEN_ENDPOINT" to "",
        "NAIS_CLUSTER_NAME" to "",
        "IVERKSETT_BASE_URL" to "",
        "TEST_BEHANDLING_ID" to ""
    )
)

private val config = ConfigurationProperties.systemProperties() overriding
    EnvironmentVariables overriding defaultProperties

private fun String.configProperty(): String = config[Key(this, stringType)]

data class Configuration(
    val azureAd: AzureAd = AzureAd(),
    val cluster: String = "NAIS_CLUSTER_NAME".configProperty(),
    val iverksettBaseUrl: String = "IVERKSETT_BASE_URL".configProperty(),
    val testBehandlingId: String = "TEST_BEHANDLING_ID".configProperty(),
) {

    data class AzureAd(
        val clientId: String = "AZURE_APP_CLIENT_ID".configProperty(),
        val clientSecret: String = "AZURE_APP_CLIENT_SECRET".configProperty(),
        val iverksettClientId: String = "EF_IVERKSETT_CLIENT_ID".configProperty(),
        val tokenEndpoint: String = "AZURE_OPENID_CONFIG_TOKEN_ENDPOINT".configProperty().removeSuffix("/")
    )
}
