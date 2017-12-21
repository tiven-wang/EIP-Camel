package wang.tiven.eipcamel.testing;

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

      rest("/books").description("Books REST service")
          .get("/").description("The list of all the books")
              .route().routeId("books-api")
              .inOut("direct:books")              
              .endRest()
          .get("/{id}").description("Details of an book by id")
              .route().routeId("book-api")
              .inOut("direct:book");
      
      from("direct:book").bean("bookRepository", "getByIsbn(${header.id})");
      
      // use script expression in route
      from("direct:books")
      .choice()
          .when().javaScript("request.headers.get('user') == 'admin'")
          	.bean("bookRepository", "getByRole(\"admin\")")
      .otherwise()
      	// use script file in route
          .to("language://javascript:resource:classpath:test-javascript.js?transform=false");
    }

}
