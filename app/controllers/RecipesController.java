package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Ingredient;
import models.Recipe;
import play.cache.SyncCacheApi;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import views.IngredientResource;
import views.RecipeResource;
import views.xml.receta;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RecipesController extends Controller {

    private final ArrayList<RecipeResource> recipes = new ArrayList<>();

    @Inject
    private FormFactory formFactory;
    @Inject
    private SyncCacheApi cache;

    public Result create(Http.Request request) {
        Form<RecipeResource> recipeForm = formFactory.form(RecipeResource.class).bindFromRequest(request);
        if (recipeForm.hasErrors()) {
            return badRequest(recipeForm.errorsAsJson());
        }
        RecipeResource newRecipe = recipeForm.get();
        Recipe recipeModel = newRecipe.toModel();
        Recipe dbRecipe = Recipe.findByName(recipeModel.getName());
        if (dbRecipe != null && newRecipe.getName().equals(dbRecipe.getName())) {
            // 409 CONFLICT
            return status(409, "Ya existe una receta con ese nombre");
        }
        Ingredient ing = new Ingredient();
        recipeModel.addIngredient(ing);
        recipeModel.save();

        ObjectNode jsonResponse = Json.newObject();
        jsonResponse.put("id", recipeModel.getId());
        return created(jsonResponse);
    }

    public Result retrieve(Http.Request request, Integer id) {
        Recipe recipe;
        Optional<Object> optRecipes = cache.get("recipe-" + id);
        if (optRecipes.isPresent()) {
            recipe = (Recipe) optRecipes.get();
        } else {
            recipe = Recipe.findById(Long.valueOf(id));
        }

        if (recipe == null) {
            return Results.notFound();
        }
        RecipeResource recipeView = new RecipeResource(recipe);
        Result result;

        boolean isAcceptJson = request.accepts("application/json");
        boolean isAcceptXML = request.accepts("application/xml");

        if (isAcceptJson) {
            result = ok(recipeView.toJson());
        } else if (isAcceptXML) {
            // https://www.playframework.com/documentation/2.8.x/ScalaTemplates
            result = ok(receta.render(
                    recipeView.getName(),
                    recipeView.getStars(),
                    recipeView.getDescription(),
                    recipeView.getIngredients()));
        } else {
            result = unsupportedMediaType("Soportado: 'application/json' o 'application/xml'");
        }

        cache.set("recipe-" + id, recipeView.toModel());

        return result;
    }

    public Result delete(Http.Request nequest, Integer id) {
        Recipe dbRecipe = Recipe.findById(Long.valueOf(id));
        if (dbRecipe != null) {
            dbRecipe.delete();
            return ok();
        }
        return notFound();
    }

    public Result update(Http.Request nequest, Integer id) {
        Form<RecipeResource> recipeForm = formFactory.form(RecipeResource.class).bindFromRequest(nequest);
        if (recipeForm.hasErrors()) {
            return badRequest(recipeForm.errorsAsJson());
        }
        Recipe dbRecipe = Recipe.findById(Long.valueOf(id));
        if (dbRecipe == null) {
            return notFound();
        }

        RecipeResource newRecipe = recipeForm.get();
        Recipe recipeModel = newRecipe.toModel();
        dbRecipe.setName(recipeModel.getName());
        dbRecipe.setStars(recipeModel.getStars());
        dbRecipe.setDescription(recipeModel.getDescription());
        dbRecipe.setIngredients(recipeModel.getIngredients());
        dbRecipe.update();
        return ok();
    }

    public Result addRecipe(Http.Request nequest, Integer id) {
        Form<IngredientResource> ingredientForm = formFactory.form(IngredientResource.class).bindFromRequest(nequest);
        if (ingredientForm.hasErrors()) {
            return badRequest(ingredientForm.errorsAsJson());
        }
        Recipe recipe = Recipe.findById(Long.valueOf(id));
        if (recipe == null) {
            return Results.notFound();
        }

        IngredientResource newIngredient = ingredientForm.get();
        Ingredient ingredientModel = newIngredient.toModel();
        recipe.addIngredient(ingredientModel);
        recipe.update();

        return ok(newIngredient.toJson());
    }

    public Result retrieveAll(Http.Request nequest) {
        // https://www.playframework.com/documentation/2.3-M1/api/java/play/db/ebean/Model.Finder.html#setMaxRows(int)
        String maxRow = "10";
        String minRow = "0";
        List<Recipe> recipes;
        Optional<Object> optRecipes = cache.get("all-recipes" + maxRow + minRow);
        if (optRecipes.isPresent()) {
            recipes = (List<Recipe>) optRecipes.get();
        } else {
            maxRow = nequest.getHeaders().get("Max-row").orElse(maxRow);
            minRow = nequest.getHeaders().get("Min-row").orElse(minRow);
            recipes = Recipe.findAll(Integer.valueOf(maxRow), Integer.valueOf(minRow));
            cache.set("all-recipes" + maxRow + minRow, recipes);
        }

        List<RecipeResource> resources = recipes.stream().map(RecipeResource::new).collect(Collectors.toList());
        JsonNode json = Json.toJson(resources);
        return ok(json);
    }
}
