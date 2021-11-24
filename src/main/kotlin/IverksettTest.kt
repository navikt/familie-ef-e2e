
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking
import no.nav.familie.ef.e2e.client.AzureAdClient
import no.nav.familie.ef.e2e.client.IverksettClient
import no.nav.familie.ef.e2e.config.Configuration
import no.nav.familie.ef.e2e.domene.IverksettRequest

class IverksettTest {
    private val configuration = Configuration()
    private val azureAdClient = AzureAdClient(configuration.azureAd)
    private val iverksettClient = IverksettClient(configuration.iverksettBaseUrl, azureAdClient)

    fun runTests() {
        `kall mot iverksett returnerer status 200`()
    }

    private fun `kall mot iverksett returnerer status 200`() {
        val iverksettResponse = runBlocking {
            iverksettClient.iverksett(
                IverksettRequest(testIverksettDto, "testFil.pdf".toByteArray())
            )
        }
        iverksettResponse.status.value shouldBe 200
    }

    private val testIverksettDto = """
        {
          "fagsak": {
            "fagsakId": "234bed7c-b1d3-11eb-9999-0242ac130003",
            "eksternId": 12,
            "stønadstype": "BARNETILSYN"
          },
          "behandling": {
            "behandlingId": "234bed7c-b1d3-11eb-8529-0242ac130003",
            "forrigeBehandlingId": "999abcde-fabc-11eb-8529-0242ac130003",
            "eksternId": 14,
            "behandlingType": "FØRSTEGANGSBEHANDLING",
            "behandlingÅrsak": "NYE_OPPLYSNINGER",
            "vilkårsvurderinger": [
              {
                "vilkårType":"AKTIVITET",
                "resultat":"IKKE_AKTUELL",
                "delvilkårsvurderinger": [
                  {
                    "resultat": "IKKE_AKTUELL",
                    "vurderinger": [
                      {
                        "regelId": "BOR_OG_OPPHOLDER_SEG_I_NORGE",
                        "svar": "ANDRE_FORELDER_MEDLEM_MINST_5_ÅR_AVBRUDD_MINDRE_ENN_10_ÅR",
                        "begrunnelse": "string"
                      }
                    ]
                  }
                ]
              },
              {
                "vilkårType": "SAGT_OPP_ELLER_REDUSERT",
                "resultat": "OPPFYLT",
                "delvilkårsvurderinger": [
                  {
                    "resultat": "OPPFYLT",
                    "vurderinger": [
                      {
                        "regelId": "SAGT_OPP_ELLER_REDUSERT",
                        "svar": "JA",
                        "begrunnelse": "string"
                      }
                    ]
                  }
                ]
              }
            ],
            "aktivitetspliktInntrefferDato":null,
            "kravMottatt": null
          },
          "søker": {
            "barn": [
              {
                "personIdent": "1234567910",
                "termindato": null
              }
            ],
            "tilhørendeEnhet": "4489",
            "adressebeskyttelse": "STRENGT_FORTROLIG",
            "personIdent": "12345678901"
          },
          "vedtak": {
            "resultat": "AVSLÅTT",
            "opphørÅrsak": "PERIODE_UTLØPT",
            "vedtaksdato": "2021-05-10",
            "vedtaksperioder": [
              {
                "aktivitet": "BARNET_ER_SYKT",
                "fraOgMed": "2021-06-10",
                "periodeType": "HOVEDPERIODE",
                "tilOgMed": "2021-06-10"
              }
            ],
            "saksbehandlerId": "A123456",
            "beslutterId": "B123456",
            "tilkjentYtelse": {
              "andelerTilkjentYtelse": [
                {
                  "kildeBehandlingId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                  "fraOgMed": "2021-05-10",
                  "periodetype": "MÅNED",
                  "tilOgMed": "2021-05-10",
                  "beløp": 0,
                  "inntekt": 0,
                  "samordningsfradrag": 0,
                  "inntektsreduksjon": 0
                }
              ]
            },
            "tilbakekreving": {
              "tilbakekrevingsvalg": "OPPRETT_TILBAKEKREVING_MED_VARSEL",
              "tilbakekrevingMedVarsel": {
                "varseltekst": "varsel",
                "sumFeilutbetaling": 1,
                "perioder": [
                  {
                    "fom": "2021-01-01",
                    "tom": "2021-01-01"
                  }
                ]
              }
            }
          }
        }
    """.trimIndent()
}