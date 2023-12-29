package com.sg.camelmicroservicea.routes.a;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SampleAMQRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        //send message to an AMQ queue with a sample body
        from("timer:mytimer?period=5000")
                .routeId("my-sample-amq-route")
                .autoStartup(false)
                .setBody(constant("HELLO from Camel!"))
                .log("Sending ${body} to sample AMQ queue")
                .to("activemq:queue:sample");
    }
}
