package com.sg.camelmicroservicea.beans;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OutboundNameAddress {
    private String name;
    private String address;
}
