package soap;

import static com.consol.citrus.ws.actions.SoapActionBuilder.soap;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.testng.annotations.Test;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = com.consol.citrus.config.CitrusSpringConfig.class)
public class SoapStubTest extends TestNGCitrusSpringSupport {

  @Test(description = "SOAP getUser request test")
  @CitrusTest
  public void getUserTest() {
    run(soap()
        .client("soapClient")
        .send()
        .message()
        .contentType("text/xml;charset=UTF-8")
        .header("SOAPAction", "getUser")
        .body(
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                "xmlns:usr=\"http://example.com/user\">" +
                "<soapenv:Body>" +
                "<usr:getUserRequest>" +
                "<usr:id>123</usr:id>" +
                "</usr:getUserRequest>" +
                "</soapenv:Body>" +
                "</soapenv:Envelope>"
        )
    );

    run(soap()
        .client("soapClient")
        .receive()
        .message()
        .body(
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                "xmlns:usr=\"http://example.com/user\">" +
                "<soapenv:Body>" +
                "<usr:getUserResponse>" +
                "<usr:name>Test user</usr:name>" +
                "<usr:score>78</usr:score>" +
                "</usr:getUserResponse>" +
                "</soapenv:Body>" +
                "</soapenv:Envelope>"
        )
    );
  }
}
