package com.Dou888311.recipes.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.Dou888311.recipes.DTO.Id;
import com.Dou888311.recipes.Entity.Recipe;
import com.Dou888311.recipes.Repository.RecipeRepository;
import com.Dou888311.recipes.Repository.UserRepository;
import com.Dou888311.recipes.UserSettings.UserDetailsImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class RecipeService {

    private RecipeRepository recipeRepository;
    private UserRepository userRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    public Id addRecipe(Recipe recipe) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl details = (UserDetailsImpl) auth.getPrincipal();
        recipe.setUser(userRepository.findUserByEmail(details.getUsername()));
        recipe.setDate(LocalDateTime.now());
        recipeRepository.save(recipe);
        return new Id(recipe.getId());
    }

    public Recipe getRecipe(long id) {
        if (recipeRepository.existsById(id)) {
            return recipeRepository.findRecipeById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public HttpStatus deleteRecipe(long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl details = (UserDetailsImpl) auth.getPrincipal();
        String username = details.getUsername();
        if (recipeRepository.existsById(id)) {
            Recipe recipe = recipeRepository.findRecipeById(id);
            if (!recipe.getUser().getEmail().equals(username)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
            recipeRepository.delete(recipeRepository.findRecipeById(id));
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public Recipe updateRecipe(Recipe recipe, long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl details = (UserDetailsImpl) auth.getPrincipal();
        String username = details.getUsername();
        Recipe oldRecipe = recipeRepository.findRecipeById(id);
        if (!recipeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (!oldRecipe.getUser().getEmail().equals(username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        recipe.setId(id);
        recipe.setDate(LocalDateTime.now());
        recipe.setUser(userRepository.findUserByEmail(username));
        recipeRepository.save(recipe);
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    public List<Recipe> searchRecipe(String category, String name) {
        if (Objects.equals(category, null) && Objects.equals(name, null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (!Objects.equals(category, null) && !Objects.equals(name, null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (!Objects.equals(name, null)) {
            return recipeRepository.findAllByNameContainsIgnoreCaseOrderByDateDesc(name);
        }
        if (!Objects.equals(category, null)) {
            return recipeRepository.findAllByCategoryEqualsIgnoreCaseOrderByDateDesc(category);
        }
        return null;
    }
}
