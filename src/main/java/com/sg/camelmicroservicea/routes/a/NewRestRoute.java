package com.sg.camelmicroservicea.routes.a;

import com.sg.camelmicroservicea.beans.NameAddress;
import com.sg.camelmicroservicea.processor.InboundMessageProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class NewRestRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        // configure a jetty server on port 8080
        restConfiguration()
                .component("jetty")
                .host("0.0.0.0")
                .port(8081)
                .bindingMode(RestBindingMode.json)
                .enableCORS(true);

/*        rest("rest-demo")
                .produces("application/json")
                .post("/hello").type(NameAddress.class).route()
                .routeId("new-rest-route")
                .log(LoggingLevel.INFO, "Processing ${body}")
                .process( new InboundMessageProcessor())
                .log(LoggingLevel.INFO, "After transformation ${body}")
                .convertBodyTo(String.class)
                .to("file:src/data/output?fileName=outputFile.csv&fileExist=append&appendChars=\\n");*/

/*        rest("rest-demo")
                .produces("application/json")
                .post("/hello").type(NameAddress.class).route()
                .routeId("new-rest-route")
                .log(LoggingLevel.INFO, "Processing ${body}")
                .multicast()
                    .to("jpa:com.sg.camelmicroservicea.beans.NameAddress")
                    .setBody(simple("${in.body}"))
                    .setHeader("CamelJmsMessageType", constant("Text"))
                    .to("activemq:queue:demo?exchangePattern=InOnly")
                .end();*/

        // add a custom response object
        rest("rest-demo")
                .produces("application/json")
                .post("/hello")
                .type(NameAddress.class)
                .route()
                .routeId("rest-processing-route")
                .log(LoggingLevel.INFO, "Processing ${body}")
                .to("direct:jpaProcessing")  // Send to JPA processing
                .to("direct:amqProcessing") // Send to ActiveMQ processing
                .setBody(simple("${in.body}"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))// Set the content type explicitly
                .end().unmarshal().json(JsonLibrary.Jackson);


        from("direct:jpaProcessing")
                .routeId("jpa-processing-route")
                .to("jpa:com.sg.camelmicroservicea.beans.NameAddress")
                .log("Message stored in JPA: ${body}");

        from("direct:amqProcessing")
                .routeId("amq-processing-route")
                .marshal().json(JsonLibrary.Jackson)
                .setHeader("CamelJmsMessageType", constant("Text"))
                .to("activemq:queue:demo?exchangePattern=InOnly")
                .log("Message sent to ActiveMQ: ${in.body}");

    }
}
