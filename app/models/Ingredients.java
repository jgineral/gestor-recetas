package models;

import io.ebean.Finder;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
public class Ingredients extends BaseModel {
    private static final Finder<Long, Ingredients> find = new Finder<>(Ingredients.class);
    private String name;
    private Double amount;
    private String measure;

    @ManyToMany(mappedBy = "ingredients")
    public Set<Recipe> recipes;

    // - métodos de acceso para leer la Base de datos.

    public static Ingredients findById(Long id) {
        return find.byId(id);
    }

    public static Ingredients findByName(String name) {
        return find.query()
                .where()
                .eq("name", name)
                .findOne();
    }

    // getter & setters
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
}
