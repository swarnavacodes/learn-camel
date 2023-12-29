package com.sg.camelmicroservicea.routes.a;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class RestToAMQDemoRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {


        rest("/api")
                .post("/toAmq")
                .consumes("application/json")
                .route()
                .routeId("RestToAMQ")  // Adding the route id
                .setBody(simple("${in.body}"))
                .to("activemq:queue:demo?exchangePattern=InOnly");
    }
}
