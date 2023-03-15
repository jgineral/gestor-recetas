package views;

import com.fasterxml.jackson.databind.JsonNode;
import models.Ingredients;
import models.Recipe;
import play.data.validation.Constraints;
import play.libs.Json;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.LinkedList;
import java.util.List;

public class RecipeResource {
    @Constraints.Required
    @NotBlank
    private String name;
    @Max(5)
    @Min(0)
    private Integer stars;
    private String description;
    @Constraints.Required
    @Valid
    @NotEmpty
    private List<IngredientResource> ingredients = new LinkedList<>();

    // Neceistamos un constructor por defecto para que compile al hacer el formulario: FormFactory
    public RecipeResource() {
    }

    public RecipeResource(Recipe recipe) {
        super();
        this.name = recipe.getName();
        this.stars = recipe.getStars();
        this.description = recipe.getDescription();
        this.ingredients = getIngredientsModel(recipe.getIngredients());
    }

    private List<IngredientResource> getIngredientsModel( List<Ingredients> ingredientsModel) {
        List<IngredientResource> newIngredients = new LinkedList<>();
        IngredientResource newIngredient;
        for (Ingredients ingredient : ingredientsModel) {
            newIngredient = new IngredientResource(ingredient);
            newIngredients.add(newIngredient);
        }
        return newIngredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<IngredientResource> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientResource> ingredients) {
        this.ingredients = ingredients;
    }

    public JsonNode toJson() {
        return Json.toJson(this);
    }

    public Recipe toModel() {
        Recipe recipe = new Recipe();
        recipe.setName(this.name);
        recipe.setStars(this.stars);
        recipe.setDescription(this.description);
        recipe.setIngredients(setIngredientsModel(this.ingredients));
        return recipe;
    }

    private List<Ingredients> setIngredientsModel(List<IngredientResource> ingredientsResource) {
        List<Ingredients> newIngredients = new LinkedList<>();
        for (IngredientResource ingredient : ingredientsResource) {
            newIngredients.add(ingredient.toModel());
        }

        return newIngredients;
    }
}
