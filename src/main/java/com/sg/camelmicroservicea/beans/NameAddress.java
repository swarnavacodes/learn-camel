package com.sg.camelmicroservicea.beans;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "name_address")
@Data
public class NameAddress {

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(name = "house_number")
    private String houseNumber;
    private String city;
    private String province;

    @Column(name = "postal_code")
    private String postalCode;
}
