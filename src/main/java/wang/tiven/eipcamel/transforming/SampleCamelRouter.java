package wang.tiven.eipcamel.transforming;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * A simple Camel route that triggers from a timer and calls a bean and prints to system out.
 * <p/>
 * Use <tt>@Component</tt> to make Camel auto detect this route when starting.
 */
@Component
public class SampleCamelRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
    	from("direct:json").
	    	unmarshal().
	    	  xmljson().
	    	    to("mock:xml");
    	
    	from("direct:simple")
    	 .transform().simple("Hello ${body}!")
    	   .to("mock:result");
    	
    	from("direct:js")
	   	 .transform().javaScript("'Hello ' + request.body + '!'")
	   	   .to("mock:result");
    	
    	from("direct:processor")
    	 .process(new Processor() {
		    public void process(Exchange exchange) throws Exception {
		      exchange.getIn().setBody("Hello " + exchange.getIn().getBody() + "!");
		    }
		  })
   	    .to("mock:result");
    	
    	from("direct:bean")
	   	 .bean(new HelloWorld())
	  	 .to("mock:result");
    }
    
}
