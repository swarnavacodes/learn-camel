package com.sg.camelmicroservicea.processor;


import com.sg.camelmicroservicea.beans.NameAddress;
import com.sg.camelmicroservicea.beans.OutboundNameAddress;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InboundMessageProcessor implements Processor {

    Logger logger = LoggerFactory.getLogger(InboundMessageProcessor.class);
    @Override
    public void process(Exchange exchange) throws Exception {


            NameAddress nameAddress = exchange.getIn().getBody(NameAddress.class);
            //logger.info("Processed file: {}", body.toString());
            exchange.getIn().setBody(new OutboundNameAddress(nameAddress.getName(), returnConcatinatedAddress(nameAddress)));
    }

    private String returnConcatinatedAddress(NameAddress nameAddress) {

        return nameAddress.getHouseNumber() + " " + nameAddress.getCity() + " " + nameAddress.getProvince() + " " + nameAddress.getPostalCode();
    }
}
