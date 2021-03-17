package com.example.demo.controller;

import com.example.demo.domain.Ingredient;
import com.example.demo.domain.Order;
import com.example.demo.domain.Taco;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.repository.TacoRepostory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DesignTacoController.class);

    private final IngredientRepository ingredientRepository;

    private TacoRepostory tacoRepostory;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepository,TacoRepostory tacoRepostory){
        this.ingredientRepository = ingredientRepository;
        this.tacoRepostory = tacoRepostory;
    }

    @GetMapping
    public String showDesignForm(){
        return "design";
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model){
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(i->ingredients.add(i));

        Ingredient.Type [] types = Ingredient.Type.values();
        for(Ingredient.Type type: types){
            model.addAttribute(type.toString().toLowerCase(),filterByType(ingredients,type));
        }
    }

    @PostMapping
    public String processDesign(@Valid Taco taco, Errors errors,@ModelAttribute Order order){
        if(errors.hasErrors()){
            return "design";
        }
        CopyStringToObject(taco);
        logger.info("Processing taco:"+taco);

        Taco saved = tacoRepostory.save(taco);
        order.addDesign(saved);

        return "redirect:/orders/current";
    }

    private void CopyStringToObject(Taco taco) {
        List<Ingredient> ingredients = new ArrayList<>();
        for(String info : taco.getIngredientsString()){
            Ingredient ingredient = new Ingredient(info,"",null);
            ingredients.add(ingredient);
        }
        taco.setIngredients(ingredients);
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type){
        return ingredients.stream().filter(x->x.getType().equals(type)).collect(Collectors.toList());
    }
}
