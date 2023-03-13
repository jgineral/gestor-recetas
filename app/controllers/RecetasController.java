package controllers;

import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import views.IngredienteView;
import views.RecetaView;

import java.util.List;

public class RecetasController extends Controller {

    public Result create(Http.Request request) {
        Result result;
        String name = request.queryString("name").orElse("");
        String stars = request.queryString("stars").orElse("");
        String desc = request.queryString("description").orElse("");
        String ingredientName = request.queryString("ingredientName").orElse("");

        RecetaView recetaView = new RecetaView();
        recetaView.setName(name);
        recetaView.setStars(Integer.valueOf(stars));
        recetaView.setDescription(desc);

        IngredienteView ingredient = new IngredienteView();
        ingredient.setName(ingredientName);

        recetaView.getIngredients().add(ingredient);


        result = Results.created(recetaView.toJson())
                .as("application/json");

        return result;
    }
}
