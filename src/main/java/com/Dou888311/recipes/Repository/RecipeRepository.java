package com.Dou888311.recipes.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Dou888311.recipes.Entity.Recipe;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Recipe findRecipeById(long id);
    Boolean existsById(long id);
    List<Recipe> findAllByNameContainsIgnoreCaseOrderByDateDesc(String name);
    List<Recipe> findAllByCategoryEqualsIgnoreCaseOrderByDateDesc(String category);
}
