package com.sg.camelmicroservicea.routes.a;

import com.sg.camelmicroservicea.beans.NameAddress;
import com.sg.camelmicroservicea.processor.InboundMessageProcessor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class NewRestRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        // configure a jetty server on port 8080
        restConfiguration().component("jetty").host("0.0.0.0").port(8081)
                .bindingMode(RestBindingMode.json).enableCORS(true);

/*        rest("rest-demo")
                .produces("application/json")
                .post("/hello").type(NameAddress.class).route()
                .routeId("new-rest-route")
                .log(LoggingLevel.INFO, "Processing ${body}")
                .process( new InboundMessageProcessor())
                .log(LoggingLevel.INFO, "After transformation ${body}")
                .convertBodyTo(String.class)
                .to("file:src/data/output?fileName=outputFile.csv&fileExist=append&appendChars=\\n");*/

        rest("rest-demo")
                .produces("application/json")
                .post("/hello").type(NameAddress.class).route()
                .routeId("new-rest-route")
                .log(LoggingLevel.INFO, "Processing ${body}")
                .to("jpa:com.sg.camelmicroservicea.beans.NameAddress");

    }
}
