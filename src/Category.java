import java.util.HashSet;

public class Category {
    private String id;
    private String name;
    // Static HashSet, no duplicates no insertion order
    private static HashSet<Category> allCategories = new HashSet<>();

    // Constructor
    public Category(){}
    public Category(String id, String name) {
        this.name = name;
        this.id = id;

        allCategories.add(this); // Add the current category to the static array
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

    public static Category findCategoryWithId(String id) {
        for (Category category : allCategories) {
            if (category.getId().equals(id)) { 
                return category;
            }
        }
        return null;
    }

    // =============SETTERS AND GETTERS=============

    /**
     * @return String return the id
     */
    public Boolean IdExists(String id) {
        for (Category category : allCategories) {
            if(category.getId().equals(id))
                return true;
        }
        return false;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
    
    public String getId(){
        return id;
    }

    /**
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }


}