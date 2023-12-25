package com.sg.camelmicroservicea.routes.a;

import com.sg.camelmicroservicea.processor.InboundMessageProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.beanio.BeanIODataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



@Component
public class LegacyFileRoute extends RouteBuilder {

    Logger logger = LoggerFactory.getLogger(LegacyFileRoute.class);
    BeanIODataFormat beanIODataFormat = new BeanIODataFormat("InboundMessageBeanIOMapping.xml", "inputMessageStream");

    @Override
    public void configure() throws Exception {
        from("file:src/data/input?fileName=inputFile.csv")
                .routeId("my-legacy-file-route")
                .split(body().tokenize("\n",1,true))
                .unmarshal(beanIODataFormat)
                    .process( new InboundMessageProcessor())
                .convertBodyTo(String.class)
                    .to("file:src/data/output?fileName=outputFile.csv&fileExist=append&appendChars=\\n")
                .end();
                //.setBody(constant("input.txt"))
                //.log("Processed file: ${body}");
    }
}
