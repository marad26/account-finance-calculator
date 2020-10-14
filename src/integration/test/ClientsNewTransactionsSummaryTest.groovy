import com.company.accountfinancecalculator.AccountFinanceCalculatorApplication
import org.apache.http.HttpEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import org.json.JSONObject
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import org.springframework.test.context.ContextConfiguration
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

@ContextConfiguration(classes = AccountFinanceCalculatorApplication.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ClientsNewTransactionsSummaryTest extends Specification {

    @Shared
    CloseableHttpClient client

    HttpPost httpPost

    def setupSpec() {
        client = HttpClients.createDefault()
    }

    def setup() {
        httpPost = new HttpPost("http://localhost:8080/calculators/newTransactionsSummary")
        httpPost.setHeader("Accept", "application/json")
        httpPost.setHeader("Content-type", "application/json")
    }

    @Unroll
    def "should return new operations summary"() {
        given:
        StringEntity entity = new StringEntity(getStringFromJsonFile(requestJSONUrl))
        httpPost.setEntity(entity)

        when:
        def response = client.execute(httpPost)

        then:
        response.getStatusLine().getStatusCode() == 200
        def responseJson = getJSONObjectFromResponseEntity(response.getEntity())
        JSONAssert.assertEquals(responseJson, new JSONObject(getStringFromJsonFile(responseJSONUrl)), true)

        where:
        requestJSONUrl                                     | responseJSONUrl
        "/clientsNewOperationsSummary.json"                | "/clientsNewOperationsSummaryResponse.json"
        "/clientsNewOperationsSummary-sampleContract.json" | "/clientsNewOperationsSummary-sampleContractResponse.json"
    }

    def "should fail on request with incorrect data"() {
        given:
        StringEntity entity = new StringEntity(getStringFromJsonFile("/invalidInput.json"))
        httpPost.setEntity(entity)

        when:
        def response = client.execute(httpPost)

        then:
        response.getStatusLine().getStatusCode() == 400
    }

    def getStringFromJsonFile(String path) {
        return new String(new ClassPathResource(path).getInputStream().readAllBytes())
    }

    def getJSONObjectFromResponseEntity(HttpEntity responseEntity) {
        return new JSONObject(EntityUtils.toString(responseEntity, "UTF-8"))
    }

    def cleanupSpec() {
        client.close()
    }
}
