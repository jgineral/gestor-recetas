package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.RecetaView;

import java.util.ArrayList;

public class RecetasController extends Controller {

    ArrayList<RecetaView> recipes = new ArrayList<>();

    public Result create(Http.Request request) {
        // TODO: Checkear Content-Type para futurua inmplementación, permitir HTML y JSON
        if (!request.hasBody()) {
            return badRequest("La petición requiere de un body");
        }
        JsonNode jsonBody = request.body().asJson();

        ObjectMapper mapper = new ObjectMapper();
        RecetaView newRecipe = null;
        try {
            newRecipe = mapper.treeToValue(jsonBody, RecetaView.class);
        } catch (JsonProcessingException e) {
            return badRequest("JSON Formato incorrecto");
        }

        if (newRecipe.getName().isEmpty() || newRecipe.getIngredients().isEmpty()) {
            return badRequest("Faltan parámetros obligatorios, nombre o ingrediente");
        }
        recipes.add(newRecipe);
        return created("Se va a crear la receta: "+ newRecipe.getName());
    }

    public Result retrieve(Http.Request request, String id) {
        return null;
    }

    public Result delete(Http.Request nequest, String id) {
        return  null;
    }

    public Result update(Http.Request nequest, String id) {
        return null;
    }

    public Result retrieveAll(Http.Request nequest) {
        return null;
    }
}
