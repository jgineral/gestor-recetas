package views;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;

import java.util.LinkedList;
import java.util.List;

public class RecetaView {
    private String name;
    private Integer stars;
    private String description;
    private List<IngredienteView> ingredients = new LinkedList<>();

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

    public List<IngredienteView> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredienteView> ingredients) {
        this.ingredients = ingredients;
    }

    public JsonNode toJson() {
        return Json.toJson(this);
    }
}
