import java.util.HashSet;

public class Category {
    private String id;
    private String name;
    // Static HashSet, no duplicates no insertion order
    private static HashSet<Category> allCategories = new HashSet<>();

    // Constructor
    public Category(String id, String name) {
        this.name = name;
        this.id = id;

        if (id != null) {
            if (newCategoryId(id)) {
                allCategories.add(this); // Add the current category to the static array
            }
        }
    }

    // Iterates through everything in the allCategories hashset and prints it
    public void printAllCategories() {
        for (Category e : allCategories) {
            System.out.println(e.id + "-" + e.name);
        }
    }

    // Overrides toString
    @Override
    public String toString() {
        return this.id + " " + this.name;
    }

    // Returns false if the categoryId already exists, else returns true
    private boolean newCategoryId(String id){
        for (Category category : allCategories) {
            if (id.equals(category.id)){
                return false;
            }
        }
        return true;
    }

    // =============SETTERS AND GETTERS=============
    public String getId() {
        return id;
    }
}