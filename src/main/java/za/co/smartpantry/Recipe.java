package za.co.smartpantry;
public class Recipe {
    public long id;
    public String name, ingredients, steps;
    public Recipe(long id, String name, String ingredients, String steps) {
        this.id=id; this.name=name; this.ingredients=ingredients; this.steps=steps;
    }
    public String[] ingredientArray() {
        return ingredients.split(",");
    }
}
