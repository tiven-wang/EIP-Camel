package wang.tiven.eipcamel.errorhandling;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
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
      restConfiguration()
              .contextPath("/camel-rest-jpa").apiContextPath("/api-doc")
                  .apiProperty("api.title", "Camel REST API")
                  .apiProperty("api.version", "1.0")
                  .apiProperty("cors", "true")
                  .apiContextRouteId("doc-api")
              .bindingMode(RestBindingMode.json);
      
      errorHandler(defaultErrorHandler().maximumRedeliveries(10).redeliveryDelay(100));
      
      rest("/books").description("Books REST service")
          .get("/").description("The list of all the books")
              .route().routeId("books-api")
              .inOut("direct:books")              
              .endRest()
          .get("/{id}").description("Details of an book by id")
              .route().routeId("book-api")
              .inOut("direct:book");
      
      from("direct:books")
      	.errorHandler(deadLetterChannel("log:DLC").maximumRedeliveries(3).redeliveryDelay(100).onPrepareFailure(new Processor() {
			@Override
			public void process(Exchange exchange) throws Exception {
				Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
				exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 503);
				exchange.getIn().setBody(exception.getMessage());
			}
		})).onException(ServiceError.class).handled(true).process(new Processor() {
			@Override
			public void process(Exchange exchange) throws Exception {
				Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
				exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 501);
				exchange.getIn().setBody(exception.getMessage());
			}
		}).end()
      	.bean("bookRepository", "getAll").end();
      
      from("direct:book").process(new Processor() {
			@Override
			public void process(Exchange exchange) throws Exception {
				if (!"CamelinAction".equalsIgnoreCase(exchange.getIn().getHeader("id").toString())) {
					Message fault = exchange.getOut();
					fault.setFault(true);
					fault.setBody("Unknown book!");
				}
			}
		}).bean("bookRepository", "getByIsbn(${header.id})");
      
    }

}
