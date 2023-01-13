package scraper;

public interface Scraper {

    String nameHotels();
    String hotelLocationScraper(String name);
    String hotelRatingsScraper(String name);
    String hotelCommentsScraper(String name, int page);
    String hotelServicesScraper(String name);


}
