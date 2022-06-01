package com.Dou888311.recipes.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.Dou888311.recipes.DTO.Id;
import com.Dou888311.recipes.Entity.Recipe;
import com.Dou888311.recipes.Services.RecipeService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class RecipeController {

    private RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/api/recipe/new")
    public Id postRecipe(@RequestBody @Valid Recipe recipe) {
        return recipeService.addRecipe(recipe);
    }

    @GetMapping("/api/recipe/{id}")
    public Recipe getRecipe(@PathVariable long id) {
        return recipeService.getRecipe(id);
    }

    @DeleteMapping("/api/recipe/{id}")
    public HttpStatus deleteRecipe(@PathVariable long id) {
        return recipeService.deleteRecipe(id);
    }

    @PutMapping("api/recipe/{id}")
    public Recipe updateRecipe(@RequestBody @Valid Recipe recipe, @PathVariable long id) {
        return recipeService.updateRecipe(recipe, id);
    }

    @GetMapping("api/recipe/search")
    public List<Recipe> searchRecipes(@RequestParam (required = false) String category, @RequestParam (required = false)
                                      String name) {
        return recipeService.searchRecipe(category, name);
    }
}
