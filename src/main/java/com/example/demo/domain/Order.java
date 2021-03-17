package com.example.demo.domain;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "taco_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Timestamp placedAt;

    @NotBlank(message = "Name is required")
    @Basic
    @Column(name = "name",nullable = false,length = 50)
    private String name;


    @NotBlank(message = "Street is required")
    @Basic
    @Column(name = "street",nullable = false,length = 50)
    private String street;

    @Basic
    @Column(name = "City",nullable = false,length = 50)
    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    @Basic
    @Column(name = "state",nullable = false,length = 2)
    private String state;

    @NotBlank(message = "Zip code is required")
    @Basic
    @Column(name = "zip",nullable = false,length = 2)
    private String zip;

//    @CreditCardNumber(message = "Not a valid credit card number")
    @NotBlank(message = "Not a valid credit card number")
    @Basic
    @Column(name = "cc_number",nullable = false,length = 16)
    private String ccNumber;

//    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",message = "Must be formatted MM/YY")
    @NotBlank(message = "Must be formatted MM/YY")
    @Basic
    @Column(name = "cc_expiration",nullable = false,length = 5)
    private String ccExpiration;

    @NotBlank(message = "Invalid CVV")
//    @Digits(integer = 3, fraction = 0,message = "Invalid CVV")
    @Basic
    @Column(name = "cc_cvv",nullable = false,length = 3)
    private String ccCvv;

    @ManyToMany(targetEntity = Taco.class)
    private List<Taco> tacos = new ArrayList<>();

    public void addDesign(Taco taco){
        this.tacos.add(taco);
    }

    @PrePersist
    void placedAt(){
        new Timestamp(new Date().getTime());
    }
}
