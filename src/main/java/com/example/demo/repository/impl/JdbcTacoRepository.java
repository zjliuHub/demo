package com.example.demo.repository.impl;

import com.example.demo.domain.Ingredient;
import com.example.demo.domain.Taco;
import com.example.demo.repository.TacoRepostory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

@Repository
public class JdbcTacoRepository implements TacoRepostory {

    private JdbcTemplate jdbcTemplate;

    public JdbcTacoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Taco save(Taco taco) {
        long tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);
        for (Ingredient ingredient : taco.getIngredients()) {
            saveIngredientToTaco(ingredient, tacoId);
        }
        return taco;
    }

    private long saveTacoInfo(Taco taco) {
        PreparedStatementCreatorFactory preparedStatementCreatorFactory = new PreparedStatementCreatorFactory("insert into taco(`name`,`create_at`) values (?,?)", Types.VARCHAR, Types.TIMESTAMP);
        preparedStatementCreatorFactory.setReturnGeneratedKeys(true);
        PreparedStatementCreator psc = preparedStatementCreatorFactory.newPreparedStatementCreator(Arrays.asList(taco.getName(), new Timestamp(new Date().getTime())));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, keyHolder);
        return keyHolder.getKey().longValue();
    }

    private void saveIngredientToTaco(Ingredient ingredient, long tacoId) {
        jdbcTemplate.update("insert into taco_ingredients(`taco_id`,`ingredients_id`) value (?,?)", tacoId, ingredient.getId());
    }
}
