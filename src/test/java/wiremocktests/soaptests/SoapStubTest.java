package wiremocktests.soaptests;

import static com.consol.citrus.ws.actions.SoapActionBuilder.soap;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;

@ContextConfiguration(classes = com.consol.citrus.config.CitrusSpringConfig.class)
public class SoapStubTest extends TestNGCitrusSpringSupport {

  @Test
  @CitrusTest
  public void getUserTest() {
    run(soap()
        .client("soapClient")
        .send()
        .message()
        .contentType("text/xml"));

    run(soap()
        .client("soapClient")
        .receive()
        .message()
        .body("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<usr:getUserResponse xmlns:usr=\"http://example.com/user\">\n"
            + "<usr:name>Test user</usr:name>\n"
            + "<usr:score>78</usr:score>\n"
            + "</usr:getUserResponse>")
    );
  }

}