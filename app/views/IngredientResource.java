package views;

import com.fasterxml.jackson.databind.JsonNode;
import models.Ingredient;
import play.data.validation.Constraints;
import play.data.validation.Constraints.Validatable;
import play.data.validation.Constraints.Validate;
import play.data.validation.ValidationError;
import play.libs.Json;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Validate
public class IngredientResource implements Validatable<List<ValidationError>> {
    @Constraints.Required
    @NotBlank
    private String name;
    private Double amount;
    private String measure;

    public IngredientResource() {
    }

    public IngredientResource(Ingredient ingredients) {
        super();
        this.name = ingredients.getName();
        this.amount = ingredients.getAmount();
        this.measure = ingredients.getMeasure();
    }

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

    public Ingredient toModel() {
        Ingredient ingredients = new Ingredient();
        ingredients.setName(this.name);
        ingredients.setAmount(this.amount);
        ingredients.setMeasure(this.measure);

        return ingredients;
    }

    @Override
    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<>();
        if (amount != null && amount >=0 && (measure == null || measure.isEmpty())) {
            errors.add(new ValidationError("measure", "measure is needed if amount"));
        }
        if (measure != null && !measure.isEmpty() && (amount == null || amount < 0)) {
            errors.add(new ValidationError("amount", "amount is needed if measure"));
        }

        return errors;
    }
}
