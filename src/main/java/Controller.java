import scraper.*;
import api.ApiBooking;


public class Controller {

    public Controller(){
        Scraper scraper = new BookingScraper();
        new ApiBooking(scraper);
    }
}
