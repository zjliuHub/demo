package com.example.demo.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC,force = true)
@Entity
public class Ingredient {
    @Id
    @Column(name = "id",nullable = false,length = 4)
    private final String id;

    @Basic
    @Column(name = "name",nullable = false,length = 25)
    private final String name;

    @Basic
    @Column(name = "type",nullable = false,length = 10)
    private final Type type;

    public static enum Type{
        WRAP,PROTEIN,VEGGIES,CHEESE,SAUCE
    }

}
