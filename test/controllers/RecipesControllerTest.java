package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.test.WithApplication;

public class RecipesControllerTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }
/*
    @Test
    public void testCreate() {
        JsonNode testJson = createPOSTJSON();
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(POST)
                .uri("/recipes")
                .bodyJson(testJson);

        Result result = route(app, request);
        assertEquals(CREATED, result.status());
    }
    /*@Test
    public void testRetrieve() {
        RecetasController recipesController = new RecetasController();
        JsonNode testJson = createPOSTJSON();
        Http.Request req = new Http.RequestBuilder()
                .method(POST)
                .uri("/recipes")
                .bodyJson(testJson).build();
        recipesController.create(req);

        Http.Request request = new Http.RequestBuilder()
                .method(GET)
                .uri("/recipe/1")
                .bodyJson(testJson).build();

        Result result = recipesController.retrieve(request, 1);
        assertEquals(OK, result.status());
    }*/

    private JsonNode createPOSTJSON() {
        try {
            ObjectMapper mapper = new ObjectMapper();

            ObjectNode recipe = mapper.createObjectNode();
            recipe.put("name", "Café");
            recipe.put("stars", 3);
            recipe.put("description", "Café con leche");

            ObjectNode ingredient1 = mapper.createObjectNode();
            ingredient1.put("name", "Grano de Café");
            ingredient1.put("amount", 150);
            ingredient1.put("measure", "miligramos");

            ArrayNode arrayIngredients = mapper.createArrayNode();
            arrayIngredients.add(ingredient1);

            // append address to user
            recipe.set("ingredients", arrayIngredients);

            return recipe;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
