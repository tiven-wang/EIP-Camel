package wang.tiven.eipcamel.quartz;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
public class StarterApplicationTests {
	
	@EndpointInject(uri = "mock:result")     
	protected MockEndpoint result;

	@Autowired
	private CamelContext camelContext;

	@Test
	public void testTimer() throws InterruptedException {

		result.expectedMessageCount(5);
		result.assertIsSatisfied();
	}

}
