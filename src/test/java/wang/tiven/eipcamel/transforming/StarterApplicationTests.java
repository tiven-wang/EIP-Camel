package wang.tiven.eipcamel.transforming;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.DefaultMessage;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
public class StarterApplicationTests {

	@Autowired
	private CamelContext camelContext;
	
	@Produce(uri = "direct:json")
    protected ProducerTemplate jsonProducer;
	
	@Produce(uri = "direct:simple")
    protected ProducerTemplate simpleProducer;
	
	@Produce(uri = "direct:js")
    protected ProducerTemplate jsProducer;
	
	@Produce(uri = "direct:processor")
    protected ProducerTemplate pProducer;
	
	@Produce(uri = "direct:bean")
    protected ProducerTemplate bProducer;
	
	@EndpointInject(uri = "mock:result")     
	protected MockEndpoint result;

	@Test
	public void testJson2XML() throws IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		
		Exchange exchange = new DefaultExchange(camelContext);
		Message message = new DefaultMessage(camelContext);
		
		File data = new File(classLoader.getResource("data.json").getFile());
		message.setBody(FileUtils.readFileToString(data, "UTF-8"));
		exchange.setIn(message);
		
		jsonProducer.send(exchange);
		
		File result = new File(classLoader.getResource("result.xml").getFile());
		assertEquals(FileUtils.readFileToString(result, "UTF-8"), exchange.getIn().getBody().toString());
	}

	@Test
	public void testTransformSimple() throws InterruptedException {
		
		result.expectedBodiesReceived("Hello world!");
		
		simpleProducer.sendBody("world");
		
		result.assertIsSatisfied();
	}
	
	@Test
	public void testTransformJavaScript() throws InterruptedException {
		
		result.expectedBodiesReceived("Hello world!");
		
		jsProducer.sendBody("world");
		
		result.assertIsSatisfied();
	}
	
	@Test
	public void testProcessor() throws InterruptedException {
		
		result.expectedBodiesReceived("Hello world!");
		
		pProducer.sendBody("world");
		
		result.assertIsSatisfied();
	}
	
	@Test
	public void testBean() throws InterruptedException {
		
		result.expectedBodiesReceived("Hello world!");
		
		bProducer.sendBody("world");
		
		result.assertIsSatisfied();
	}
}
