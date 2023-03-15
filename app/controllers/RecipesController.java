package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Recipe;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import views.RecipeResource;
import views.xml.receta;

import javax.inject.Inject;
import java.util.ArrayList;

public class RecipesController extends Controller {

    private final ArrayList<RecipeResource> recipes = new ArrayList<>();

    @Inject
    private FormFactory formFactory;

    public Result create(Http.Request request) {
        Form<RecipeResource> recipeForm = formFactory.form(RecipeResource.class).bindFromRequest(request);
        if (recipeForm.hasErrors()) {
            return badRequest(recipeForm.errorsAsJson());
        }
        RecipeResource newRecipe = recipeForm.get();
        Recipe recipeModel = newRecipe.toModel();
        recipeModel.save();

        ObjectNode jsonResponse = Json.newObject();
        jsonResponse.put("id", recipeModel.getId());

        return created(jsonResponse);
    }

    public Result retrieve(Http.Request request, Integer id) {
        Recipe recipe = Recipe.findById(Long.valueOf(id));
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
            result = ok(receta.render(recipeView.getName(), recipeView.getStars(), recipeView.getDescription()));
        } else {
            result = unsupportedMediaType("Soportado: 'application/json' o 'application/xml'");
        }

        return result;
    }

    public Result delete(Http.Request nequest, Integer id) {
        return  null;
    }

    public Result update(Http.Request nequest, Integer id) {
        return null;
    }

    public Result retrieveAll(Http.Request nequest) {
        JsonNode json = Json.toJson(recipes);
        return ok(json);
    }
}
