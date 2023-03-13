package controllers;

import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import views.RecetaView;

public class RecetasController extends Controller {

    public Result create(Http.Request request) {
        Result result;
        String name = request.queryString("name").orElse("");
        String stars = request.queryString("stars").orElse("");

        RecetaView recetaView = new RecetaView();
        recetaView.setName(name);
        recetaView.setStars(Integer.valueOf(stars));


        result = Results.created(recetaView.toJson())
                .as("application/json");

        return result;
    }
}
