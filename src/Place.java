import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

public class Place {
    private String id;
    private String name;
    // Static HashSet, no duplicates no insertion order
    private HashSet<Category> categories = new HashSet<>();
    // Static LinkedHashSet, no duplicates but maintains insertion order
    private LinkedHashSet<Review> reviews = new LinkedHashSet<>();

    // Constructor
    public Place(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Adds a category
    public void addCategory(Category category) {
        categories.add(category);
    }

    // Checks if this place has a specific category
    public boolean hasCategory(String categoryId) {
        for (Category category : categories) {
            if(category != null && category.getId().equals(categoryId)){
                    return true;
            }
        }
        return false;
    }

    // Adds a review
    public void addReview(Review review) {
        reviews.add(review);
    }

    // Prints the reviews for this place
    public void printReviews(boolean backward) {
    if (!backward) {
        for (Review review : reviews) {
            System.out.println(review);
        }
    } else {
        // Convert LinkedHashSet to a List and reverse the List
        List<Review> reviewList = new ArrayList<>(reviews);
        Collections.reverse(reviewList);
        for (Review review : reviewList) {
            System.out.println(review);
            }
        }
    }

    public String toString(){
        return "id: " + id + ", name: " + name + "\t" + categories;
    }


    // =============SETTERS AND GETTERS=============

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return String return the id
     */
    public String getId() {
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

    public LinkedHashSet<Review> getReview() {
        return reviews;
    }

}