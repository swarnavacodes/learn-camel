package com.sg.camelmicroservicea;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
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
class CamelMicroserviceAApplicationTests {

	@Autowired
	private CamelContext context;

	@EndpointInject("mock:result")
	protected MockEndpoint mockEndpoint;

	@Test
	public void testMyfirstTimerRouteTest() throws Exception {

		String expectedBody = "This is my first message";

		mockEndpoint.expectedBodiesReceived(expectedBody);
		mockEndpoint.expectedMessageCount(1);

		AdviceWith.adviceWith(context, "my-first-timer-route", routeBuilder -> {
			routeBuilder.weaveAddLast().to(mockEndpoint);
		});

		context.start();
		mockEndpoint.assertIsSatisfied();
	}

}
