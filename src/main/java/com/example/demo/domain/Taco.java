package com.example.demo.domain;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Taco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    @Column(name = "name", nullable = false, length =50)
    private String name;

    @Basic
    @Column(name = "create_At",nullable = false)
    private Timestamp createAt;

    @ManyToMany(targetEntity = Ingredient.class)
    private List<Ingredient> ingredients;

    @Transient
    private List<String> ingredientsString;

    @PrePersist
    void createdAt() {
        this.createAt = new Timestamp(new Date().getTime());
    }
}
