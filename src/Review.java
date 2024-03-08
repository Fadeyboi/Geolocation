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
    public String toString() {
        return text + "- " + rating + "/5";
    }
}