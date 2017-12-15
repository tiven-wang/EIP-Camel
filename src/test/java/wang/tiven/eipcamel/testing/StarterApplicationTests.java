package wang.tiven.eipcamel.testing;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.DefaultMessage;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
public class StarterApplicationTests {

	@Autowired
	private CamelContext camelContext;

	@SuppressWarnings("unchecked")
	@Test
	public void testBooks() {

		Exchange exchange = new DefaultExchange(camelContext);
		exchange.setIn(new DefaultMessage(camelContext));
		exchange = camelContext.createProducerTemplate().send("direct:books", exchange);
		Message message = exchange.getIn();
		assertEquals(9, ((List<Book>) message.getBody()).size());
	}

	@Test
	public void testBook() {

		Exchange exchange = new DefaultExchange(camelContext);
		Message in = new DefaultMessage(camelContext);
		in.setHeader("id", 123);
		exchange.setIn(in);
		exchange = camelContext.createProducerTemplate().send("direct:book", exchange);
		Message message = exchange.getIn();
		assertEquals("123", ((Book) message.getBody()).getIsbn());
	}
}
