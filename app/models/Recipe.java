package models;

import io.ebean.Finder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Recipe extends BaseModel {

    // Permite leer de la Base de datos.
    private static final Finder<Long, Recipe> find = new Finder<>(Recipe.class);
    private String name;
    private Integer stars;
    private String description;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Ingredients> ingredients = new LinkedList<>();

    // - métodos de acceso para leer la Base de datos.

    public static Recipe findById(Long id) {
        return find.byId(id);
    }

    public static Recipe findByName(String name) {
        return find.query()
                .where()
                .eq("name", name)
                .findOne();
    }

    public static List<Recipe> findGreaterThanStars(Integer stars) {
        return find.query()
                .where()
                .gt("stars", stars)
                .orderBy("name")
                .setMaxRows(30)
                .setFirstRow(0)
                .findList();
    }

    // - getters & setters
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

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }
}
