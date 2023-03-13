package views;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;

public class IngredienteView {
    private String name;
    private Double amount;
    private String measure;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public JsonNode toJson() {
        return Json.toJson(this);
    }
}
