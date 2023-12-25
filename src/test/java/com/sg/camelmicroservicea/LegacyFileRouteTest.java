package com.sg.camelmicroservicea;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.UseAdviceWith;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@CamelSpringBootTest
@UseAdviceWith
public class LegacyFileRouteTest {

    @Autowired
    private CamelContext context;

    @EndpointInject("mock:result")
    protected MockEndpoint mockEndpoint;

    @Autowired
    private ProducerTemplate producerTemplate;

   /* @Test
    public void testLegacyFileRouteToEndpoint() throws Exception {
        //Setup mock endpoint
        mockEndpoint.expectedBodiesReceived("Sample Input file");
        mockEndpoint.expectedMessageCount(1);

        //Tweak the route definition
        AdviceWith.adviceWith(context, "my-legacy-file-route", routeBuilder -> {
            routeBuilder.weaveByToUri("file:*").replace().to(mockEndpoint);
        });


        //Start the context and validate the results
        context.start();
        mockEndpoint.assertIsSatisfied();
    }*/

    @Test
    public void testLegacyFileRouteFromEndpoint() throws Exception {
        String expectedBody = "OutboundNameAddress(name=Mike, address= 111  Toronto  ON  M5F3D2)";
        //Setup mock endpoint
        mockEndpoint.expectedBodiesReceived(expectedBody);
        mockEndpoint.expectedMessageCount(1);

        //Tweak the route definition
        AdviceWith.adviceWith(context, "my-legacy-file-route", routeBuilder -> {
            routeBuilder.replaceFromWith("direct:mockStart");
            routeBuilder.weaveByToUri("file:*").replace().to(mockEndpoint);
        });


        //Start the context and validate the results
        context.start();
        producerTemplate.sendBody("direct:mockStart", "name, house_number, city, province, postal_code\nMike, 111, Toronto, ON, M5F3D2");
        mockEndpoint.assertIsSatisfied();
    }
}
