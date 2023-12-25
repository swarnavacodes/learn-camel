package com.sg.camelmicroservicea.routes.a;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyFirstTimerRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        // START SNIPPET: timer
        from("timer:first-timer?period=2s")
                .setBody(constant("This is my first message"))
                .routeId("my-first-timer-route");
                //.log(LoggingLevel.INFO, "${body}");
        // END SNIPPET: timer
    }
}
