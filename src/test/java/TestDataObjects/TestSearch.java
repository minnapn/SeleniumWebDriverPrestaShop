package TestDataObjects;

public class TestSearch {
    public String searchString;
    public int expectedNmbOfHits;

    public TestSearch(String searchString, int expectedNmbOfHits) {
        this.searchString = searchString;
        this.expectedNmbOfHits = expectedNmbOfHits;
    }
}
