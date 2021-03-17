package com.example.demo.repository.impl;


import com.example.demo.domain.Ingredient;
import com.example.demo.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcIngredientRepository implements IngredientRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcIngredientRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Iterable<Ingredient> findAll() {
        return jdbcTemplate.query("select * from ingredient",this::mapRowToIngredient);
    }

    @Override
    public Ingredient findOne(String id) {
        return jdbcTemplate.queryForObject("select * from ingredient where id =?" , this::mapRowToIngredient, id);
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        jdbcTemplate.update("insert into Ingredient (`id`,`name`,`type` value (?,?,?))",ingredient.getId(),ingredient.getName(),ingredient.getType().toString());
        return ingredient;
    }

    private Ingredient mapRowToIngredient(ResultSet rs,int rowNum) throws SQLException{
        return new Ingredient(rs.getString("id"),rs.getString("name"),Ingredient.Type.valueOf(rs.getString("type")));
    }
}
