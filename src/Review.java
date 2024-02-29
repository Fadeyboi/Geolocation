public class Review {
    private String id;
    private String text;
    private int rating;

    public Review(String id, String text, int rating) {
        this.id = id;
        this.text = text;
        this.rating = rating;
    }

    @Override
    public String toString(){
        return  text + "- " + rating + "/5";
    }

    // =============SETTERS AND GETTERS=============

    /**
     * @return String return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return String return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return int return the rating
     */
    public int getRating() {
        return rating;
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

}